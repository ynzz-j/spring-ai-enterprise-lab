package com.ynzz.lab.chapter07.common;

public class TicketEvent {
    private final String tenantId;
    private final String ticketId;
    private final String title;
    private final String content;
    private final String source;

    public TicketEvent(String tenantId, String ticketId, String title, String content, String source) {
        this.tenantId = tenantId;
        this.ticketId = ticketId;
        this.title = title;
        this.content = content;
        this.source = source;
    }

    public String getTenantId() {
        return tenantId;
    }

    public String getTicketId() {
        return ticketId;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getSource() {
        return source;
    }
}

