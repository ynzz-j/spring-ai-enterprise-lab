package com.ynzz.lab.aicenter.service;

import com.ynzz.lab.aicenter.api.dto.ToolIntentRequest;
import com.ynzz.lab.aicenter.api.dto.ToolIntentResponse;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

@Service
public class ToolIntentMvpService {
    private static final Pattern ORDER_ID = Pattern.compile("O[0-9]{12,}");
    private final ChatClient chatClient;
    private final Environment environment;

    public ToolIntentMvpService(ChatClient chatClient, Environment environment) {
        this.chatClient = chatClient;
        this.environment = environment;
    }

    public ToolIntentResponse inspect(ToolIntentRequest request) {
        String question = safe(request.question());
        String intentType = isWrite(question) ? "WRITE" : "READ";
        String orderId = extractOrderId(question);

        if (!isRealMode()) {
            return new ToolIntentResponse(intentType, orderId, "demo intent by local rules");
        }

        try {
            String reason = chatClient.prompt()
                    .system("你是企业工具调用意图识别助手。只解释用户是在读查询还是写操作，不要执行任何工具。")
                    .user("tenantId=" + safe(request.tenantId()) + ", operatorId=" + safe(request.operatorId())
                            + ", question=" + question)
                    .call()
                    .content();
            return new ToolIntentResponse(intentType, orderId, normalize(reason));
        } catch (RuntimeException ex) {
            return new ToolIntentResponse(intentType, orderId, "fallback intent by local rules");
        }
    }

    private boolean isWrite(String question) {
        return question.contains("改成")
                || question.contains("修改")
                || question.contains("删除")
                || question.contains("更新")
                || question.contains("发货");
    }

    private String extractOrderId(String question) {
        Matcher matcher = ORDER_ID.matcher(question);
        return matcher.find() ? matcher.group() : "UNKNOWN";
    }

    private boolean isRealMode() {
        for (String profile : environment.getActiveProfiles()) {
            if ("real".equalsIgnoreCase(profile)) {
                return true;
            }
        }
        return "real".equalsIgnoreCase(environment.getProperty("ai-center.mode"));
    }

    private String normalize(String content) {
        String value = safe(content).replace('\n', ' ').replace('\r', ' ').trim();
        return value.isEmpty() ? "model returned empty reason" : value;
    }

    private String safe(String value) {
        return value == null ? "" : value;
    }
}
