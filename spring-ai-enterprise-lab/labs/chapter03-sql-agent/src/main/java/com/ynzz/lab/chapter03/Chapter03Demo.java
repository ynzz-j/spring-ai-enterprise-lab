package com.ynzz.lab.chapter03;

import com.ynzz.lab.chapter03.agent.HttpSqlGenerateService;
import com.ynzz.lab.chapter03.agent.ReadOnlySqlExecutor;
import com.ynzz.lab.chapter03.agent.SqlGenerateService;
import com.ynzz.lab.chapter03.agent.SqlAgentService;
import com.ynzz.lab.chapter03.agent.SqlResultSummarizer;
import com.ynzz.lab.chapter03.agent.StubSqlGenerateService;
import com.ynzz.lab.chapter03.common.SqlQueryRequest;
import com.ynzz.lab.chapter03.common.SqlQueryResult;
import com.ynzz.lab.chapter03.safety.SqlSafetyEngine;

public class Chapter03Demo {
    public static void main(String[] args) {
        SqlGenerateService sqlGenerateService = createSqlGenerateService();
        SqlAgentService service = new SqlAgentService(
                sqlGenerateService,
                new SqlSafetyEngine(),
                new ReadOnlySqlExecutor(),
                new SqlResultSummarizer());

        run(service, "统计本月销售额最高的 10 个商品");
        run(service, "查询本月退款订单数量");
        run(service, "删除所有测试订单数据");
        run(service, "查询所有客户手机号");
    }

    private static SqlGenerateService createSqlGenerateService() {
        StubSqlGenerateService fallback = new StubSqlGenerateService();
        String baseUrl = System.getenv("AI_CENTER_BASE_URL");
        if (baseUrl != null && baseUrl.trim().length() > 0) {
            System.out.println("sqlGenerateService=HttpSqlGenerateService, baseUrl=" + baseUrl);
            return new HttpSqlGenerateService(baseUrl.trim(), fallback);
        }
        System.out.println("sqlGenerateService=StubSqlGenerateService, reason=AI_CENTER_BASE_URL not set");
        return fallback;
    }

    private static void run(SqlAgentService service, String question) {
        System.out.println("=== question: " + question + " ===");
        SqlQueryResult result = service.query(new SqlQueryRequest("demo", "u1001", question));
        System.out.println(result.toJson());
        System.out.println();
    }
}
