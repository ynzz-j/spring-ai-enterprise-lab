package com.ynzz.lab.chapter07.policy;

public class AssigneeRecommendation {
    private final String assignee;
    private final String reason;

    public AssigneeRecommendation(String assignee, String reason) {
        this.assignee = assignee;
        this.reason = reason;
    }

    public String getAssignee() {
        return assignee;
    }

    public String getReason() {
        return reason;
    }
}

