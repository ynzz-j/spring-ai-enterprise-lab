package com.ynzz.lab.aicenter.api.dto;

public record OrderSummaryRequest(
        String tenantId,
        String operatorId,
        String orderId,
        String orderText
) {
}
