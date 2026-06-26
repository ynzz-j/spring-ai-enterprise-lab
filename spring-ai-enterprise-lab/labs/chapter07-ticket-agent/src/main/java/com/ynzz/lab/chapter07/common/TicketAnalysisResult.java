package com.ynzz.lab.chapter07.common;

import java.util.ArrayList;
import java.util.List;

public class TicketAnalysisResult {
    private final String ticketId;
    private final String summary;
    private final String category;
    private final String priority;
    private final boolean slaRisk;
    private final String recommendedAssignee;
    private final String recommendationReason;
    private final String assignmentStatus;
    private final List<String> maskedFields;

    public TicketAnalysisResult(String ticketId,
                                String summary,
                                String category,
                                String priority,
                                boolean slaRisk,
                                String recommendedAssignee,
                                String recommendationReason,
                                String assignmentStatus,
                                List<String> maskedFields) {
        this.ticketId = ticketId;
        this.summary = summary;
        this.category = category;
        this.priority = priority;
        this.slaRisk = slaRisk;
        this.recommendedAssignee = recommendedAssignee;
        this.recommendationReason = recommendationReason;
        this.assignmentStatus = assignmentStatus;
        this.maskedFields = new ArrayList<String>(maskedFields);
    }

    public String getTicketId() {
        return ticketId;
    }

    public String getRecommendedAssignee() {
        return recommendedAssignee;
    }

    public String toJson() {
        return "{\n"
                + "  \"ticketId\": \"" + escape(ticketId) + "\",\n"
                + "  \"summary\": \"" + escape(summary) + "\",\n"
                + "  \"category\": \"" + category + "\",\n"
                + "  \"priority\": \"" + priority + "\",\n"
                + "  \"slaRisk\": " + slaRisk + ",\n"
                + "  \"recommendedAssignee\": \"" + recommendedAssignee + "\",\n"
                + "  \"recommendationReason\": \"" + escape(recommendationReason) + "\",\n"
                + "  \"assignmentStatus\": \"" + assignmentStatus + "\",\n"
                + "  \"maskedFields\": " + stringArray(maskedFields) + "\n"
                + "}";
    }

    private String stringArray(List<String> values) {
        StringBuilder builder = new StringBuilder("[");
        for (int i = 0; i < values.size(); i++) {
            if (i > 0) {
                builder.append(", ");
            }
            builder.append("\"").append(escape(values.get(i))).append("\"");
        }
        builder.append("]");
        return builder.toString();
    }

    private String escape(String value) {
        return value.replace("\\", "\\\\").replace("\"", "\\\"");
    }
}

