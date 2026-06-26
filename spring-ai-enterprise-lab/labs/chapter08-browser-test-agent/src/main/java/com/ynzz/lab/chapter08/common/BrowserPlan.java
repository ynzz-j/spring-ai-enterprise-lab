package com.ynzz.lab.chapter08.common;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BrowserPlan {
    private final String planId;
    private final String environment;
    private final String targetUrl;
    private final boolean rejected;
    private final String rejectReason;
    private final String rejectedAt;
    private final String matchedKeyword;
    private final boolean requiresApproval;
    private final List<BrowserStep> steps;

    private BrowserPlan(String planId,
                        String environment,
                        String targetUrl,
                        boolean rejected,
                        String rejectReason,
                        String rejectedAt,
                        String matchedKeyword,
                        boolean requiresApproval,
                        List<BrowserStep> steps) {
        this.planId = planId;
        this.environment = environment;
        this.targetUrl = targetUrl;
        this.rejected = rejected;
        this.rejectReason = rejectReason;
        this.rejectedAt = rejectedAt;
        this.matchedKeyword = matchedKeyword;
        this.requiresApproval = requiresApproval;
        this.steps = new ArrayList<BrowserStep>(steps);
    }

    public static BrowserPlan allowed(String planId, String environment, String targetUrl, List<BrowserStep> steps) {
        return new BrowserPlan(planId, environment, targetUrl, false, "", "", "", true, steps);
    }

    public static BrowserPlan rejected(String planId, String environment, String rejectReason, String rejectedAt, String matchedKeyword) {
        return new BrowserPlan(planId, environment, "", true, rejectReason, rejectedAt, matchedKeyword, false, new ArrayList<BrowserStep>());
    }

    public String getPlanId() {
        return planId;
    }

    public boolean isRejected() {
        return rejected;
    }

    public String getRejectReason() {
        return rejectReason;
    }

    public List<BrowserStep> getSteps() {
        return Collections.unmodifiableList(steps);
    }

    public String toJson() {
        StringBuilder builder = new StringBuilder();
        builder.append("{\n");
        builder.append("  \"planId\": \"").append(planId).append("\",\n");
        if (rejected) {
            builder.append("  \"status\": \"REJECTED\",\n");
            builder.append("  \"reason\": \"").append(rejectReason).append("\",\n");
            builder.append("  \"rejectedAt\": \"").append(rejectedAt).append("\"");
            if (matchedKeyword != null && matchedKeyword.length() > 0) {
                builder.append(",\n  \"matchedKeyword\": \"").append(escape(matchedKeyword)).append("\"");
            }
            builder.append("\n}");
            return builder.toString();
        }
        builder.append("  \"status\": \"PENDING_CONFIRMATION\",\n");
        builder.append("  \"environment\": \"").append(environment).append("\",\n");
        builder.append("  \"targetUrl\": \"").append(escape(targetUrl)).append("\",\n");
        builder.append("  \"requiresApproval\": ").append(requiresApproval).append(",\n");
        builder.append("  \"steps\": [");
        for (int i = 0; i < steps.size(); i++) {
            if (i > 0) {
                builder.append(", ");
            }
            builder.append(steps.get(i).toJson());
        }
        builder.append("]\n");
        builder.append("}");
        return builder.toString();
    }

    private String escape(String value) {
        return value.replace("\\", "\\\\").replace("\"", "\\\"");
    }
}
