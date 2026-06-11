package com.ynzz.lab.chapter02.common;

public class ToolAskRequest {
    private final String tenantId;
    private final String operatorId;
    private final String question;

    public ToolAskRequest(String tenantId, String operatorId, String question) {
        this.tenantId = tenantId;
        this.operatorId = operatorId;
        this.question = question;
    }

    public String getTenantId() {
        return tenantId;
    }

    public String getOperatorId() {
        return operatorId;
    }

    public String getQuestion() {
        return question;
    }
}

