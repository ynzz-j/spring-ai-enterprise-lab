package com.ynzz.lab.aicenter.api.dto;

public record ToolIntentResponse(
        String intentType,
        String orderId,
        String reason
) {
}
