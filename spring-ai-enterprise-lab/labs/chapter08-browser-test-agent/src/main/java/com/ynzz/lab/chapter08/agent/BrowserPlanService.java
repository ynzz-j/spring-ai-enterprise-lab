package com.ynzz.lab.chapter08.agent;

import com.ynzz.lab.chapter08.common.BrowserStep;
import com.ynzz.lab.chapter08.common.BrowserPlan;
import com.ynzz.lab.chapter08.common.BrowserTestRequest;
import com.ynzz.lab.chapter08.common.BrowserAction;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BrowserPlanService {
    private final BrowserSafetyPolicy safetyPolicy;

    public BrowserPlanService(BrowserSafetyPolicy safetyPolicy) {
        this.safetyPolicy = safetyPolicy;
    }

    public BrowserPlan createPlan(BrowserTestRequest request) {
        String rejectReason = safetyPolicy.rejectReason(request);
        if (rejectReason != null) {
            return BrowserPlan.rejected(
                    "plan-rejected",
                    request.getEnvironment(),
                    rejectReason,
                    safetyPolicy.rejectedAt(request),
                    safetyPolicy.matchedKeyword(request));
        }

        List<BrowserStep> steps = new ArrayList<BrowserStep>();
        steps.add(new BrowserStep(BrowserAction.OPEN, request.getTargetUrl(), null, true));
        steps.add(new BrowserStep(BrowserAction.TYPE, "orderIdInput", extractOrderId(request.getTask()), true));
        steps.add(new BrowserStep(BrowserAction.CLICK, "searchButton", null, true));
        steps.add(new BrowserStep(BrowserAction.SCREENSHOT, "orderDetailPanel", "artifacts/screenshots/order-detail.png", true));

        return BrowserPlan.allowed("plan-001", request.getEnvironment(), request.getTargetUrl(), steps);
    }

    private String extractOrderId(String task) {
        Pattern pattern = Pattern.compile("O[0-9]{12}");
        Matcher matcher = pattern.matcher(task);
        if (matcher.find()) {
            return matcher.group();
        }
        return "";
    }
}
