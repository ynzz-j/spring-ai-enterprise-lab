package com.ynzz.lab.chapter01.gateway;

public class AuditLogService {
    public void record(String tenantId, String operatorId, String action, String detail) {
        System.out.println("[AUDIT] tenant=" + tenantId
                + ", operator=" + operatorId
                + ", action=" + action
                + ", detail=" + detail);
    }
}

