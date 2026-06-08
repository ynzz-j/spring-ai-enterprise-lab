package com.ynzz.lab.aicenter.api.dto;

import java.util.List;

public record OrderSummaryResponse(
        String orderId,
        String summary,
        String riskLevel,
        List<String> suggestedActions,
        boolean fallback
) {
}
