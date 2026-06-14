package com.ynzz.lab.chapter03.common;

public class SqlQueryRequest {
    private final String tenantId;
    private final String operatorId;
    private final String question;

    public SqlQueryRequest(String tenantId, String operatorId, String question) {
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

