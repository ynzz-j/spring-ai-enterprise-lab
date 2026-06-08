package com.ynzz.lab.chapter01.common;

public class OrderSummaryRequest {
    private final String tenantId;
    private final String operatorId;
    private final String orderId;
    private final String orderText;

    public OrderSummaryRequest(String tenantId, String operatorId, String orderId, String orderText) {
        this.tenantId = tenantId;
        this.operatorId = operatorId;
        this.orderId = orderId;
        this.orderText = orderText;
    }

    public String getTenantId() {
        return tenantId;
    }

    public String getOperatorId() {
        return operatorId;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getOrderText() {
        return orderText;
    }
}

