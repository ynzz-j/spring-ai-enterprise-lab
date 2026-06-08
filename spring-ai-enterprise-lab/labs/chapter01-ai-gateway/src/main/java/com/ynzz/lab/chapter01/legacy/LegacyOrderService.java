package com.ynzz.lab.chapter01.legacy;

import com.ynzz.lab.chapter01.common.OrderSummaryRequest;
import com.ynzz.lab.chapter01.common.OrderSummaryResult;

import java.util.LinkedHashMap;
import java.util.Map;

public class LegacyOrderService {
    private final AiGatewayClient aiGatewayClient;
    private final Map<String, String> orderStatusStore = new LinkedHashMap<String, String>();

    public LegacyOrderService(AiGatewayClient aiGatewayClient) {
        this.aiGatewayClient = aiGatewayClient;
        orderStatusStore.put("O202606050001", "DELAYED");
        orderStatusStore.put("O202606050002", "REFUND_REQUESTED");
    }

    public OrderSummaryResult assistOrder(String orderId, String orderText) {
        OrderSummaryRequest request = new OrderSummaryRequest(
                "demo",
                "u1001",
                orderId,
                orderText);
        return aiGatewayClient.summarizeOrder(request);
    }

    public String getOrderStatus(String orderId) {
        String status = orderStatusStore.get(orderId);
        return status == null ? "UNKNOWN" : status;
    }
}
