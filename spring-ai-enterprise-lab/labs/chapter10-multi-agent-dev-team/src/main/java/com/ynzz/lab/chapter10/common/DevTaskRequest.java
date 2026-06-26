package com.ynzz.lab.chapter10.common;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DevTaskRequest {
    private final String tenantId;
    private final String operatorId;
    private final String projectName;
    private final String requirement;
    private final List<String> constraints;

    public DevTaskRequest(String tenantId, String operatorId, String projectName, String requirement, List<String> constraints) {
        this.tenantId = tenantId;
        this.operatorId = operatorId;
        this.projectName = projectName;
        this.requirement = requirement;
        this.constraints = new ArrayList<String>(constraints);
    }

    public String getTenantId() {
        return tenantId;
    }

    public String getOperatorId() {
        return operatorId;
    }

    public String getProjectName() {
        return projectName;
    }

    public String getRequirement() {
        return requirement;
    }

    public List<String> getConstraints() {
        return Collections.unmodifiableList(constraints);
    }
}

