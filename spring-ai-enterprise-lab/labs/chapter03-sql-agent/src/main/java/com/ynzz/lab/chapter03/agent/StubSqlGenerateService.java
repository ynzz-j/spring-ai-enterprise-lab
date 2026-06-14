package com.ynzz.lab.chapter03.agent;

import com.ynzz.lab.chapter03.common.CandidateSql;
import com.ynzz.lab.chapter03.common.SqlQueryRequest;

public class StubSqlGenerateService implements SqlGenerateService {
    @Override
    public CandidateSql generate(SqlQueryRequest request) {
        String question = request.getQuestion();

        if (question.contains("删除")) {
            return new CandidateSql("DELETE FROM order_report WHERE status = 'TEST'");
        }

        if (question.contains("手机号")) {
            return new CandidateSql("SELECT customer_mobile FROM order_report LIMIT 20");
        }

        if (question.contains("退款")) {
            return new CandidateSql("SELECT COUNT(*) AS refund_count FROM order_report WHERE status = 'REFUND_REQUESTED'");
        }

        return new CandidateSql("SELECT product_name, SUM(amount) AS total_amount FROM order_report "
                + "WHERE order_month = '2026-06' GROUP BY product_name ORDER BY total_amount DESC LIMIT 10");
    }
}
