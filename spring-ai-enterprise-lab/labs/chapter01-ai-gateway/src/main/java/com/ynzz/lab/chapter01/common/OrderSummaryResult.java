package com.ynzz.lab.chapter01.common;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class OrderSummaryResult {
    private final String orderId;
    private final String summary;
    private final String riskLevel;
    private final List<String> suggestedActions;
    private final boolean fallback;
    private final List<String> maskedFields;

    public OrderSummaryResult(String orderId, String summary, String riskLevel,
                              List<String> suggestedActions, boolean fallback,
                              List<String> maskedFields) {
        this.orderId = orderId;
        this.summary = summary;
        this.riskLevel = riskLevel;
        this.suggestedActions = new ArrayList<String>(suggestedActions);
        this.fallback = fallback;
        this.maskedFields = new ArrayList<String>(maskedFields);
    }

    public String getOrderId() {
        return orderId;
    }

    public String getSummary() {
        return summary;
    }

    public String getRiskLevel() {
        return riskLevel;
    }

    public List<String> getSuggestedActions() {
        return Collections.unmodifiableList(suggestedActions);
    }

    public boolean isFallback() {
        return fallback;
    }

    public List<String> getMaskedFields() {
        return Collections.unmodifiableList(maskedFields);
    }

    public String toJson() {
        StringBuilder builder = new StringBuilder();
        builder.append("{\n");
        builder.append("  \"orderId\": \"").append(orderId).append("\",\n");
        builder.append("  \"summary\": \"").append(summary).append("\",\n");
        builder.append("  \"riskLevel\": \"").append(riskLevel).append("\",\n");
        builder.append("  \"suggestedActions\": ").append(toJsonArray(suggestedActions)).append(",\n");
        builder.append("  \"fallback\": ").append(fallback).append(",\n");
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
            builder.append("\"").append(values.get(i)).append("\"");
        }
        builder.append("]");
        return builder.toString();
    }
}

