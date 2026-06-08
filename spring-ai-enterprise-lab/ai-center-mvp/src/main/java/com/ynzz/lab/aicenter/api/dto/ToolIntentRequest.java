package com.ynzz.lab.aicenter.api.dto;

public record ToolIntentRequest(
        String tenantId,
        String operatorId,
        String question
) {
}
