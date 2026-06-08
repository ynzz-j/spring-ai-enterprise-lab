package com.ynzz.lab.chapter01.gateway.pipeline.filters;

import com.ynzz.lab.chapter01.common.OrderSummaryResult;
import com.ynzz.lab.chapter01.gateway.model.ModelClient;
import com.ynzz.lab.chapter01.gateway.pipeline.GatewayContext;
import com.ynzz.lab.chapter01.gateway.pipeline.GatewayFilter;
import com.ynzz.lab.chapter01.gateway.pipeline.GatewayChain;

import java.util.Collections;

/**
 * 熔断器 Filter（执行顺序：600）。
 *
 * <p>第 1 章只做最小演示：当 {@link ModelClient#isAvailable()} 返回 false 时，
 * 直接返回降级建议，不再调用模型。
 */
public class CircuitBreakerFilter implements GatewayFilter {

    private final ModelClient modelClient;

    public CircuitBreakerFilter(ModelClient modelClient) {
        this.modelClient = modelClient;
    }

    @Override
    public void doFilter(GatewayContext context, GatewayChain chain) {
        if (!modelClient.isAvailable()) {
            // 熔断器打开，直接返回降级结果（短路责任链）
            OrderSummaryResult fallback = new OrderSummaryResult(
                    context.getOrderId(),
                    "AI 模型服务暂时不可用，请稍后重试。",
                    "UNKNOWN",
                    Collections.singletonList("联系 AI 平台管理员"),
                    true,  // fallback = true
                    context.getMaskedFields()
            );
            context.setResult(fallback);
            return;
        }

        chain.doFilter(context);
    }
}
