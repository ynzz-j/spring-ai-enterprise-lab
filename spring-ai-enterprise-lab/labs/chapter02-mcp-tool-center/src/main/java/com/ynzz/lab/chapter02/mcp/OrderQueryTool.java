package com.ynzz.lab.chapter02.mcp;

import com.ynzz.lab.chapter02.legacy.LegacyOrder;
import com.ynzz.lab.chapter02.legacy.LegacyOrderApi;

public class OrderQueryTool {
    private final LegacyOrderApi legacyOrderApi;
    private final ToolResultMasker masker;
    private final ToolAuditService auditService;

    public OrderQueryTool(LegacyOrderApi legacyOrderApi, ToolResultMasker masker, ToolAuditService auditService) {
        this.legacyOrderApi = legacyOrderApi;
        this.masker = masker;
        this.auditService = auditService;
    }

    public ToolResultMasker.MaskedOrder query(String tenantId, String operatorId, String orderId) {
        auditService.record(tenantId, operatorId, "queryOrder", "orderId=" + orderId + ", mode=READ_ONLY");
        LegacyOrder order = legacyOrderApi.queryOrder(tenantId, orderId);
        return masker.mask(order);
    }
}

