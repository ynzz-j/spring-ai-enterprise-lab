package com.ynzz.lab.chapter07.agent;

public class AssignmentConfirmationService {
    private final TicketAuditService auditService;

    public AssignmentConfirmationService(TicketAuditService auditService) {
        this.auditService = auditService;
    }

    public String confirm(String ticketId, String assignee, String confirmedBy) {
        auditService.record("CONFIRM_ASSIGNMENT", ticketId, "assignee=" + assignee + ", confirmedBy=" + confirmedBy);
        return "{\"ticketId\": \"" + ticketId + "\", \"assignee\": \"" + assignee
                + "\", \"status\": \"ASSIGNED_AFTER_HUMAN_CONFIRMATION\"}";
    }
}

