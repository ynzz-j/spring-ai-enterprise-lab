package com.ynzz.lab.chapter06.common;

public class KnowledgeAskRequest {
    private final String tenantId;
    private final String operatorId;
    private final String role;
    private final String question;

    public KnowledgeAskRequest(String tenantId, String operatorId, String role, String question) {
        this.tenantId = tenantId;
        this.operatorId = operatorId;
        this.role = role;
        this.question = question;
    }

    public String getTenantId() {
        return tenantId;
    }

    public String getOperatorId() {
        return operatorId;
    }

    public String getRole() {
        return role;
    }

    public String getQuestion() {
        return question;
    }
}

