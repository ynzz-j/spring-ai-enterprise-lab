package com.ynzz.lab.chapter01.gateway.pipeline;

import com.ynzz.lab.chapter01.common.OrderSummaryRequest;
import com.ynzz.lab.chapter01.common.OrderSummaryResult;
import com.ynzz.lab.chapter01.gateway.FallbackAnswerFactory;

import java.util.List;

/**
 * GatewayPipeline 的默认实现。
 *
 * <p>执行流程：
 * <ol>
 *   <li>创建 GatewayContext（封装 Request）</li>
 *   <li>创建 GatewayChain（持有所有 Filter）</li>
 *   <li>执行责任链（chain.doFilter）</li>
 *   <li>从 context 中取出最终结果返回</li>
 *   <li>如果任何 Filter 抛出异常，走降级逻辑</li>
 * </ol>
 */
public class DefaultGatewayPipeline implements GatewayPipeline {

    private final List<GatewayFilter> filters;
    private final FallbackAnswerFactory fallbackFactory;

    public DefaultGatewayPipeline(List<GatewayFilter> filters,
                                  FallbackAnswerFactory fallbackFactory) {
        this.filters = filters;
        this.fallbackFactory = fallbackFactory;
    }

    @Override
    public OrderSummaryResult execute(OrderSummaryRequest request) {
        GatewayContext context = new GatewayContext(request);

        try {
            GatewayChain chain = new GatewayChain(filters);
            chain.doFilter(context);

            // 责任链执行完毕后，从 context 中取出结果
            if (context.getResult() != null) {
                return context.getResult();
            } else {
                // 责任链未设置结果（异常情况），走降级
                return buildFallbackResult(request, "PIPELINE_RESULT_MISSING", context);
            }
        } catch (Exception ex) {
            // 任何 Filter 抛出异常，走降级
            return buildFallbackResult(request, "PIPELINE_ERROR:" + ex.getMessage(), context);
        }
    }

    private OrderSummaryResult buildFallbackResult(OrderSummaryRequest request,
                                                    String reason,
                                                    GatewayContext context) {
        return fallbackFactory.orderSummaryFallback(
                request.getOrderId(),
                context.getMaskedFields(),
                reason
        );
    }
}
