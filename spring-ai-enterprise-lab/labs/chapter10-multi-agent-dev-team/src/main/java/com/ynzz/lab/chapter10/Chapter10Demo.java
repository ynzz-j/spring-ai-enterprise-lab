package com.ynzz.lab.chapter10;

import com.ynzz.lab.chapter10.common.DevTaskRequest;
import com.ynzz.lab.chapter10.common.DevTeamReport;
import com.ynzz.lab.chapter10.orchestrator.DevTeamOrchestrator;

import java.util.Arrays;

public class Chapter10Demo {
    public static void main(String[] args) {
        DevTeamOrchestrator orchestrator = new DevTeamOrchestrator();
        DevTeamReport report = orchestrator.run(new DevTaskRequest(
                "demo",
                "u1001",
                "legacy-order",
                "新增订单延迟预警功能：超过 48 小时未发货时提醒客服介入。",
                Arrays.asList("老系统保持 Java 8", "不能直接修改生产数据库", "需要输出测试用例")));

        System.out.println(report.toJson());
        System.out.println();
        System.out.println("=== markdown report ===");
        System.out.println(report.toMarkdown());
    }
}

