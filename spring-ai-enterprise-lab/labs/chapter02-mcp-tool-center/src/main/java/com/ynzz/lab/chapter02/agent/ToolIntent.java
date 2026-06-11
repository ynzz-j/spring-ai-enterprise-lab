package com.ynzz.lab.chapter02.agent;

public class ToolIntent {
    private final String intentType;
    private final String orderId;
    private final String reason;

    public ToolIntent(String intentType, String orderId, String reason) {
        this.intentType = intentType;
        this.orderId = orderId;
        this.reason = reason;
    }

    public String getIntentType() {
        return intentType;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getReason() {
        return reason;
    }
}
