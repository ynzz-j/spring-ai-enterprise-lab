package com.ynzz.lab.chapter07.agent;

public class TicketAuditService {
    public String record(String action, String ticketId, String detail) {
        String auditId = "audit-" + action + "-" + ticketId;
        System.out.println("[TICKET_AUDIT] auditId=" + auditId + ", action=" + action + ", ticketId=" + ticketId + ", detail=" + detail);
        return auditId;
    }
}
