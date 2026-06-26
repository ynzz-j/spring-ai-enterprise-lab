package com.ynzz.lab.chapter08.agent;

import com.ynzz.lab.chapter08.common.BrowserStep;
import com.ynzz.lab.chapter08.common.BrowserTestRequest;

public class BrowserSafetyPolicy {
    public String rejectReason(BrowserTestRequest request) {
        if (!"test".equals(request.getEnvironment())) {
            return "ONLY_TEST_ENVIRONMENT_ALLOWED";
        }
        if (!isTestAllowlisted(request.getTargetUrl())) {
            return "TARGET_URL_NOT_IN_TEST_ALLOWLIST";
        }
        String task = request.getTask();
        if (task.contains("删除") || task.contains("审批") || task.contains("支付")) {
            return "HIGH_RISK_ACTION_NOT_ALLOWED";
        }
        return null;
    }

    public String rejectedAt(BrowserTestRequest request) {
        if (!"test".equals(request.getEnvironment())) {
            return "layer-1-request";
        }
        if (!isTestAllowlisted(request.getTargetUrl())) {
            return "layer-2-url-allowlist";
        }
        if (matchedKeyword(request).length() > 0) {
            return "layer-3-task-keyword";
        }
        return "";
    }

    public String matchedKeyword(BrowserTestRequest request) {
        String task = request.getTask();
        if (task.contains("删除")) {
            return "删除";
        }
        if (task.contains("审批")) {
            return "审批";
        }
        if (task.contains("支付")) {
            return "支付";
        }
        return "";
    }

    public String rejectReason(BrowserStep step) {
        String action = step.getAction().name();
        if ("DELETE".equals(action) || "PAY".equals(action) || "APPROVE".equals(action)) {
            return "HIGH_RISK_STEP_NOT_ALLOWED";
        }
        return null;
    }

    private boolean isTestAllowlisted(String targetUrl) {
        return targetUrl.startsWith("http://localhost") || targetUrl.startsWith("http://127.0.0.1");
    }
}
