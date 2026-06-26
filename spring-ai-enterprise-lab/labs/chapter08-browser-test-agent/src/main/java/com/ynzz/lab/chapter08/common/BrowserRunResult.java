package com.ynzz.lab.chapter08.common;

public class BrowserRunResult {
    private final String planId;
    private final String status;
    private final String reason;
    private final String confirmedBy;
    private final String screenshotPath;
    private final int executedSteps;

    private BrowserRunResult(String planId, String status, String reason, String confirmedBy, String screenshotPath, int executedSteps) {
        this.planId = planId;
        this.status = status;
        this.reason = reason;
        this.confirmedBy = confirmedBy;
        this.screenshotPath = screenshotPath;
        this.executedSteps = executedSteps;
    }

    public static BrowserRunResult passed(String planId, String confirmedBy, String screenshotPath, int executedSteps) {
        return new BrowserRunResult(planId, "PASSED", "", confirmedBy, screenshotPath, executedSteps);
    }

    public static BrowserRunResult failed(String planId, String reason, int executedSteps) {
        return new BrowserRunResult(planId, "FAILED", reason, "", "", executedSteps);
    }

    public String toJson() {
        return "{\n"
                + "  \"planId\": \"" + planId + "\",\n"
                + "  \"status\": \"" + status + "\",\n"
                + "  \"reason\": \"" + reason + "\",\n"
                + "  \"confirmedBy\": \"" + confirmedBy + "\",\n"
                + "  \"screenshotPath\": \"" + screenshotPath + "\",\n"
                + "  \"executedSteps\": " + executedSteps + "\n"
                + "}";
    }
}
