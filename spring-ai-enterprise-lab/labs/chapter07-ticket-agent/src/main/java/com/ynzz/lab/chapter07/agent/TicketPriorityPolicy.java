package com.ynzz.lab.chapter07.agent;

public class TicketPriorityPolicy {
    public String priorityOf(String category, String content) {
        if ("PAYMENT".equals(category)) {
            return "HIGH";
        }
        if (content.contains("三天") || content.contains("延迟")) {
            return "MEDIUM";
        }
        return "LOW";
    }

    public boolean hasSlaRisk(String priority, String content) {
        return "HIGH".equals(priority) || content.contains("三天");
    }
}

