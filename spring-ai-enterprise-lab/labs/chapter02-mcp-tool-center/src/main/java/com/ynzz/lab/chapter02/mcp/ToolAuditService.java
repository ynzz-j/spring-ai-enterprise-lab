package com.ynzz.lab.chapter02.mcp;

public class ToolAuditService {
    public void record(String tenantId, String operatorId, String toolName, String detail) {
        System.out.println("[TOOL_AUDIT] tenant=" + tenantId
                + ", operator=" + operatorId
                + ", tool=" + toolName
                + ", detail=" + detail);
    }
}

