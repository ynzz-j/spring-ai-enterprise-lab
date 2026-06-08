package com.ynzz.lab.aicenter.service;

import com.ynzz.lab.aicenter.api.dto.SqlCandidateRequest;
import com.ynzz.lab.aicenter.api.dto.SqlCandidateResponse;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

@Service
public class SqlCandidateMvpService {
    private final ChatClient chatClient;
    private final Environment environment;

    public SqlCandidateMvpService(ChatClient chatClient, Environment environment) {
        this.chatClient = chatClient;
        this.environment = environment;
    }

    public SqlCandidateResponse generate(SqlCandidateRequest request) {
        String question = safe(request.question());
        if (!isRealMode()) {
            return new SqlCandidateResponse(demoSql(question));
        }

        try {
            String content = chatClient.prompt()
                    .system("你是企业报表 SQL 生成助手。只允许基于 order_report 生成一条候选 SQL。"
                            + "只输出 SQL 本身，不要解释。候选 SQL 后续会经过安全引擎审核。")
                    .user("可用字段：product_name, order_month, amount, status, created_at。用户问题：" + question)
                    .call()
                    .content();
            return new SqlCandidateResponse(normalizeSql(content));
        } catch (RuntimeException ex) {
            return new SqlCandidateResponse(demoSql(question));
        }
    }

    private String demoSql(String question) {
        if (question.contains("删除")) {
            return "DELETE FROM order_report WHERE status = 'TEST'";
        }
        if (question.contains("手机号")) {
            return "SELECT customer_mobile FROM order_report LIMIT 20";
        }
        if (question.contains("退款")) {
            return "SELECT COUNT(*) AS refund_count FROM order_report WHERE status = 'REFUND_REQUESTED'";
        }
        return "SELECT product_name, SUM(amount) AS total_amount FROM order_report "
                + "WHERE order_month = '2026-06' GROUP BY product_name ORDER BY total_amount DESC LIMIT 10";
    }

    private String normalizeSql(String content) {
        String value = safe(content).trim();
        value = value.replace("```sql", "").replace("```", "").trim();
        return value.isEmpty() ? "SELECT 1" : value;
    }

    private boolean isRealMode() {
        for (String profile : environment.getActiveProfiles()) {
            if ("real".equalsIgnoreCase(profile)) {
                return true;
            }
        }
        return "real".equalsIgnoreCase(environment.getProperty("ai-center.mode"));
    }

    private String safe(String value) {
        return value == null ? "" : value;
    }
}
