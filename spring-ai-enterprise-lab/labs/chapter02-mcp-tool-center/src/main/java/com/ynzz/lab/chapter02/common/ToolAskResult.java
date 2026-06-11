package com.ynzz.lab.chapter02.common;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ToolAskResult {
    private final String answer;
    private final boolean rejected;
    private final String rejectReason;
    private final List<String> toolCalls;
    private final List<String> maskedFields;

    public ToolAskResult(String answer, boolean rejected, String rejectReason,
                         List<String> toolCalls, List<String> maskedFields) {
        this.answer = answer;
        this.rejected = rejected;
        this.rejectReason = rejectReason;
        this.toolCalls = new ArrayList<String>(toolCalls);
        this.maskedFields = new ArrayList<String>(maskedFields);
    }

    public String getAnswer() {
        return answer;
    }

    public boolean isRejected() {
        return rejected;
    }

    public String getRejectReason() {
        return rejectReason;
    }

    public List<String> getToolCalls() {
        return Collections.unmodifiableList(toolCalls);
    }

    public List<String> getMaskedFields() {
        return Collections.unmodifiableList(maskedFields);
    }

    public String toJson() {
        StringBuilder builder = new StringBuilder();
        builder.append("{\n");
        builder.append("  \"answer\": \"").append(answer).append("\",\n");
        builder.append("  \"rejected\": ").append(rejected).append(",\n");
        builder.append("  \"rejectReason\": \"").append(rejectReason).append("\",\n");
        builder.append("  \"toolCalls\": ").append(toJsonArray(toolCalls)).append(",\n");
        builder.append("  \"maskedFields\": ").append(toJsonArray(maskedFields)).append("\n");
        builder.append("}");
        return builder.toString();
    }

    private String toJsonArray(List<String> values) {
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

