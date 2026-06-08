package com.ynzz.lab.aicenter.service;

import com.ynzz.lab.aicenter.api.dto.OrderSummaryRequest;
import com.ynzz.lab.aicenter.api.dto.OrderSummaryResponse;
import java.util.Arrays;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

@Service
public class OrderSummaryMvpService {
    private final ChatClient chatClient;
    private final Environment environment;

    public OrderSummaryMvpService(ChatClient chatClient, Environment environment) {
        this.chatClient = chatClient;
        this.environment = environment;
    }

    public OrderSummaryResponse summarize(OrderSummaryRequest request) {
        if (!isRealMode()) {
            return demoSummary(request, false);
        }

        try {
            String content = chatClient.prompt()
                    .system("你是企业订单处理助手。只输出一段中文处理建议，不要修改订单状态。")
                    .user("订单号：" + safe(request.orderId()) + "\n脱敏后的订单内容：" + safe(request.orderText()))
                    .call()
                    .content();

            return new OrderSummaryResponse(
                    request.orderId(),
                    normalize(content),
                    inferRisk(request.orderText()),
                    Arrays.asList("查询物流状态", "联系仓库确认发货时间", "向客户同步预计处理时间"),
                    false);
        } catch (RuntimeException ex) {
            return demoSummary(request, true);
        }
    }

    private OrderSummaryResponse demoSummary(OrderSummaryRequest request, boolean fallback) {
        String text = safe(request.orderText());
        String summary = text.contains("延迟")
                ? "客户反馈订单延迟发货，需要尽快给出处理方案。"
                : "客户订单需要人工进一步确认。";
        return new OrderSummaryResponse(
                request.orderId(),
                summary,
                inferRisk(text),
                Arrays.asList("查询物流状态", "联系仓库确认发货时间", "向客户同步预计处理时间"),
                fallback);
    }

    private boolean isRealMode() {
        for (String profile : environment.getActiveProfiles()) {
            if ("real".equalsIgnoreCase(profile)) {
                return true;
            }
        }
        return "real".equalsIgnoreCase(environment.getProperty("ai-center.mode"));
    }

    private String inferRisk(String text) {
        return safe(text).contains("退款") ? "HIGH" : "MEDIUM";
    }

    private String normalize(String content) {
        String value = safe(content).replace('\n', ' ').replace('\r', ' ').trim();
        return value.isEmpty() ? "订单需要人工进一步确认。" : value;
    }

    private String safe(String value) {
        return value == null ? "" : value;
    }
}
