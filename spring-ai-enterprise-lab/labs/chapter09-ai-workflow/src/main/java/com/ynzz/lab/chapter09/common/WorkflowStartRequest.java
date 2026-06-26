package com.ynzz.lab.chapter09.common;

public class WorkflowStartRequest {
    private final String tenantId;
    private final String operatorId;
    private final String requirementText;

    public WorkflowStartRequest(String tenantId, String operatorId, String requirementText) {
        this.tenantId = tenantId;
        this.operatorId = operatorId;
        this.requirementText = requirementText;
    }

    public String getTenantId() {
        return tenantId;
    }

    public String getOperatorId() {
        return operatorId;
    }

    public String getRequirementText() {
        return requirementText;
    }
}

