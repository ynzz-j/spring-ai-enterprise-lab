package com.ynzz.lab.chapter08;

import com.ynzz.lab.chapter08.agent.BrowserPlanService;
import com.ynzz.lab.chapter08.agent.BrowserRunService;
import com.ynzz.lab.chapter08.agent.BrowserSafetyPolicy;
import com.ynzz.lab.chapter08.agent.ScreenshotRecorder;
import com.ynzz.lab.chapter08.common.BrowserPlan;
import com.ynzz.lab.chapter08.common.BrowserTestRequest;

public class Chapter08Demo {
    public static void main(String[] args) {
        BrowserSafetyPolicy safetyPolicy = new BrowserSafetyPolicy();
        BrowserPlanService planService = new BrowserPlanService(safetyPolicy);
        BrowserRunService runService = new BrowserRunService(safetyPolicy, new ScreenshotRecorder());

        BrowserPlan plan = planService.createPlan(new BrowserTestRequest(
                "test",
                "http://localhost:8080/admin/orders",
                "搜索订单 O202606050001，并截图订单详情页"));
        print("safe test plan", plan.toJson());

        print("confirmed run", runService.run(plan, "qa-user").toJson());

        BrowserPlan prodPlan = planService.createPlan(new BrowserTestRequest(
                "prod",
                "https://admin.example.com/orders",
                "搜索订单 O202606050001，并截图订单详情页"));
        print("prod blocked", prodPlan.toJson());

        BrowserPlan deletePlan = planService.createPlan(new BrowserTestRequest(
                "test",
                "http://localhost:8080/admin/orders",
                "删除订单 O202606050001"));
        print("dangerous action blocked", deletePlan.toJson());
    }

    private static void print(String title, String content) {
        System.out.println("=== " + title + " ===");
        System.out.println(content);
        System.out.println();
    }
}
