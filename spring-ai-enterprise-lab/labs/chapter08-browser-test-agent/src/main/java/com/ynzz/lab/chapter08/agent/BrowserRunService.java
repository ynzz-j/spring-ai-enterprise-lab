package com.ynzz.lab.chapter08.agent;

import com.ynzz.lab.chapter08.common.BrowserStep;
import com.ynzz.lab.chapter08.common.BrowserPlan;
import com.ynzz.lab.chapter08.common.BrowserRunResult;
import com.ynzz.lab.chapter08.common.BrowserAction;

public class BrowserRunService {
    private final BrowserSafetyPolicy safetyPolicy;
    private final ScreenshotRecorder screenshotRecorder;

    public BrowserRunService(BrowserSafetyPolicy safetyPolicy, ScreenshotRecorder screenshotRecorder) {
        this.safetyPolicy = safetyPolicy;
        this.screenshotRecorder = screenshotRecorder;
    }

    public BrowserRunResult run(BrowserPlan plan, String confirmedBy) {
        if (plan.isRejected()) {
            return BrowserRunResult.failed(plan.getPlanId(), "PLAN_REJECTED", 0);
        }
        if (confirmedBy == null || confirmedBy.trim().length() == 0) {
            return BrowserRunResult.failed(plan.getPlanId(), "CONFIRMATION_REQUIRED", 0);
        }

        String screenshotPath = "";
        int executedSteps = 0;
        for (BrowserStep step : plan.getSteps()) {
            String rejectReason = safetyPolicy.rejectReason(step);
            if (rejectReason != null) {
                return BrowserRunResult.failed(plan.getPlanId(), rejectReason, executedSteps);
            }
            executedSteps++;
            if (BrowserAction.SCREENSHOT.equals(step.getAction())) {
                screenshotPath = screenshotRecorder.record(plan.getPlanId(), step.getTarget());
            }
        }

        return BrowserRunResult.passed(plan.getPlanId(), confirmedBy, screenshotPath, executedSteps);
    }
}
