package com.ynzz.lab.chapter09;

import com.ynzz.lab.chapter09.common.WorkflowRun;
import com.ynzz.lab.chapter09.common.WorkflowStartRequest;
import com.ynzz.lab.chapter09.runtime.WorkflowRuntime;

public class Chapter09Demo {
    public static void main(String[] args) {
        WorkflowRuntime runtime = new WorkflowRuntime();

        WorkflowRun run = runtime.start(new WorkflowStartRequest(
                "demo",
                "u1001",
                "新增订单延迟预警功能：当订单超过 48 小时未发货时，系统需要提醒客服介入。"));

        System.out.println("=== start workflow ===");
        System.out.println(run.toJson());
        System.out.println();

        runtime.approve(run, "api-design", true, "tech-lead");

        System.out.println("=== after approval ===");
        System.out.println(run.toJson());
    }
}

