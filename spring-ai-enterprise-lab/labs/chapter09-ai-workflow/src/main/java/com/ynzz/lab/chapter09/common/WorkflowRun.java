package com.ynzz.lab.chapter09.common;

import java.util.ArrayList;
import java.util.List;

public class WorkflowRun {
    private final String workflowId;
    private final String tenantId;
    private final String operatorId;
    private String status;
    private String waitingNodeId;
    private final List<WorkflowNodeSnapshot> snapshots = new ArrayList<WorkflowNodeSnapshot>();
    private final List<String> uncertainties = new ArrayList<String>();

    public WorkflowRun(String workflowId, String tenantId, String operatorId) {
        this.workflowId = workflowId;
        this.tenantId = tenantId;
        this.operatorId = operatorId;
        this.status = "RUNNING";
        this.waitingNodeId = "";
    }

    public String getWorkflowId() {
        return workflowId;
    }

    public void addSnapshot(WorkflowNodeSnapshot snapshot) {
        snapshots.add(snapshot);
    }

    public WorkflowNodeSnapshot findSnapshot(String nodeId) {
        for (WorkflowNodeSnapshot snapshot : snapshots) {
            if (snapshot.getNodeId().equals(nodeId)) {
                return snapshot;
            }
        }
        return null;
    }

    public String lastOutput() {
        for (int i = snapshots.size() - 1; i >= 0; i--) {
            WorkflowNodeSnapshot snapshot = snapshots.get(i);
            if ("SUCCESS".equals(snapshot.getStatus()) || "APPROVED".equals(snapshot.getStatus())) {
                return snapshot.getOutput();
            }
        }
        return "";
    }

    public void waitForApproval(String nodeId) {
        this.status = "WAITING_APPROVAL";
        this.waitingNodeId = nodeId;
    }

    public void complete() {
        this.status = "COMPLETED";
        this.waitingNodeId = "";
    }

    public void running() {
        this.status = "RUNNING";
        this.waitingNodeId = "";
    }

    public void addUncertainty(String uncertainty) {
        uncertainties.add(uncertainty);
    }

    public String toJson() {
        StringBuilder builder = new StringBuilder();
        builder.append("{\n");
        builder.append("  \"workflowId\": \"").append(workflowId).append("\",\n");
        builder.append("  \"tenantId\": \"").append(tenantId).append("\",\n");
        builder.append("  \"operatorId\": \"").append(operatorId).append("\",\n");
        builder.append("  \"status\": \"").append(status).append("\",\n");
        builder.append("  \"waitingNodeId\": \"").append(waitingNodeId).append("\",\n");
        builder.append("  \"snapshots\": [");
        for (int i = 0; i < snapshots.size(); i++) {
            if (i > 0) {
                builder.append(", ");
            }
            builder.append(snapshots.get(i).toJson());
        }
        builder.append("],\n");
        builder.append("  \"uncertainties\": ").append(stringArray(uncertainties)).append("\n");
        builder.append("}");
        return builder.toString();
    }

    private String stringArray(List<String> values) {
        StringBuilder builder = new StringBuilder("[");
        for (int i = 0; i < values.size(); i++) {
            if (i > 0) {
                builder.append(", ");
            }
            builder.append("\"").append(values.get(i).replace("\"", "\\\"")).append("\"");
        }
        builder.append("]");
        return builder.toString();
    }
}
