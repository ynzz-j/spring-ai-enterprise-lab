package com.ynzz.lab.chapter01.gateway;

import java.util.Arrays;

import com.ynzz.lab.chapter01.common.OrderSummaryResult;
import com.ynzz.lab.chapter01.gateway.model.ModelClient;

/**
 * Stub Model 实现（课程演示用）。
 *
 * <p>不依赖真实 AI API Key，直接返回固定结果。
 * 通过 {@link #setAvailable(boolean)} 控制可用性，演示熔断器效果。
 */
public class StubModelClient implements ModelClient {

    private boolean available = true;

    @Override
    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    @Override
    public OrderSummaryResult summarize(String orderId, String maskedOrderText) {
        if (!available) {
            throw new IllegalStateException("stub model is unavailable");
        }

        String riskLevel = maskedOrderText.contains("退款") ? "HIGH" : "MEDIUM";
        String summary = maskedOrderText.contains("延迟")
                ? "客户反馈订单延迟发货，需要尽快给出处理方案。"
                : "客户订单需要人工进一步确认。";

        return new OrderSummaryResult(
                orderId,
                summary,
                riskLevel,
                Arrays.asList("查询物流状态", "联系仓库确认发货时间", "向客户同步预计处理时间"),
                false,
                Arrays.asList());
    }
}
