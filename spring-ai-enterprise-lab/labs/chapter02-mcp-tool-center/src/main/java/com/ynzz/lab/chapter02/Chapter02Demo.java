package com.ynzz.lab.chapter02;

import com.ynzz.lab.chapter02.agent.HttpToolIntentClient;
import com.ynzz.lab.chapter02.agent.LocalToolIntentClient;
import com.ynzz.lab.chapter02.agent.ToolCallingAgent;
import com.ynzz.lab.chapter02.agent.ToolIntentClient;
import com.ynzz.lab.chapter02.common.ToolAskRequest;
import com.ynzz.lab.chapter02.common.ToolAskResult;
import com.ynzz.lab.chapter02.legacy.LegacyOrderApi;
import com.ynzz.lab.chapter02.mcp.OrderQueryTool;
import com.ynzz.lab.chapter02.mcp.ToolAuditService;
import com.ynzz.lab.chapter02.mcp.ToolPermissionPolicy;
import com.ynzz.lab.chapter02.mcp.ToolResultMasker;

public class Chapter02Demo {
    public static void main(String[] args) {
        ToolPermissionPolicy permissionPolicy = new ToolPermissionPolicy();
        ToolIntentClient localIntentClient = new LocalToolIntentClient(permissionPolicy);
        ToolIntentClient intentClient = createIntentClient(localIntentClient);
        ToolCallingAgent agent = new ToolCallingAgent(
                permissionPolicy,
                new OrderQueryTool(new LegacyOrderApi(), new ToolResultMasker(), new ToolAuditService()),
                intentClient);

        run(agent, "帮我查询 O202606050001 这个订单现在有什么异常");
        run(agent, "把 O202606050001 订单状态改成已发货");
    }

    private static ToolIntentClient createIntentClient(ToolIntentClient fallback) {
        String baseUrl = System.getenv("AI_CENTER_BASE_URL");
        if (baseUrl != null && baseUrl.trim().length() > 0) {
            System.out.println("toolIntentClient=HttpToolIntentClient, baseUrl=" + baseUrl);
            return new HttpToolIntentClient(baseUrl.trim(), fallback);
        }
        System.out.println("toolIntentClient=LocalToolIntentClient, reason=AI_CENTER_BASE_URL not set");
        return fallback;
    }

    private static void run(ToolCallingAgent agent, String question) {
        System.out.println("=== question: " + question + " ===");
        ToolAskResult result = agent.ask(new ToolAskRequest("demo", "u1001", question));
        System.out.println(result.toJson());
        System.out.println();
    }
}
