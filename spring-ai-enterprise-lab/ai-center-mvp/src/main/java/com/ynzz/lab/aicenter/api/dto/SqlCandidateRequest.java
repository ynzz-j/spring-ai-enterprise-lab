package com.ynzz.lab.aicenter.api.dto;

public record SqlCandidateRequest(
        String tenantId,
        String operatorId,
        String question
) {
}
