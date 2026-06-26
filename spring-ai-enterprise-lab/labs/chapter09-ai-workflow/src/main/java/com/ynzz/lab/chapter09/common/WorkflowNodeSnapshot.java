package com.ynzz.lab.chapter09.common;

public class WorkflowNodeSnapshot {
    private final String nodeId;
    private String status;
    private final String input;
    private String output;
    private String approvedBy;
    private String errorCode;
    private int retryCount;
    private final String createdAt;
    private String updatedAt;

    public WorkflowNodeSnapshot(String nodeId, String status, String input, String output) {
        this.nodeId = nodeId;
        this.status = status;
        this.input = input;
        this.output = output;
        this.approvedBy = "";
        this.errorCode = "";
        this.retryCount = 0;
        this.createdAt = "2026-06-15T10:00:00";
        this.updatedAt = "2026-06-15T10:00:00";
    }

    public String getNodeId() {
        return nodeId;
    }

    public String getOutput() {
        return output;
    }

    public String getStatus() {
        return status;
    }

    public void approve(String approvedBy) {
        this.status = "APPROVED";
        this.approvedBy = approvedBy;
        this.updatedAt = "2026-06-15T10:01:00";
    }

    public void fail(String errorCode) {
        this.status = "FAILED";
        this.errorCode = errorCode;
        this.retryCount++;
        this.updatedAt = "2026-06-15T10:02:00";
    }

    public String toJson() {
        return "{"
                + "\"nodeId\": \"" + nodeId + "\", "
                + "\"status\": \"" + status + "\", "
                + "\"input\": \"" + escape(input) + "\", "
                + "\"output\": \"" + escape(output) + "\", "
                + "\"approvedBy\": \"" + approvedBy + "\", "
                + "\"errorCode\": \"" + errorCode + "\", "
                + "\"retryCount\": " + retryCount + ", "
                + "\"createdAt\": \"" + createdAt + "\", "
                + "\"updatedAt\": \"" + updatedAt + "\""
                + "}";
    }

    private String escape(String value) {
        return value.replace("\\", "\\\\").replace("\"", "\\\"");
    }
}
