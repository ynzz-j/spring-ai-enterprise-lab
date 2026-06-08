package com.ynzz.lab.chapter01.gateway;

import com.ynzz.lab.chapter01.common.OrderSummaryRequest;
import com.ynzz.lab.chapter01.common.OrderSummaryResult;
import com.ynzz.lab.chapter01.gateway.pipeline.DefaultGatewayPipeline;
import com.ynzz.lab.chapter01.gateway.pipeline.GatewayFilter;
import com.ynzz.lab.chapter01.gateway.pipeline.GatewayPipeline;

import java.util.List;

/**
 * AI Gateway 的核心服务（Facade 层）。
 *
 * <p>职责：组装责任链（Pipeline），暴露 {@link #summarize(OrderSummaryRequest)} 接口。
 *
 * <p>Pipeline 执行顺序（从小到大）：
 * <ol>
 *   <li>ValidationFilter     (100) - 参数校验</li>
 *   <li>IdempotencyFilter   (200) - 幂等校验</li>
 *   <li>RateLimitFilter      (300) - 限流</li>
 *   <li>MaskingFilter        (400) - 脱敏</li>
 *   <li>AuditRequestFilter  (500) - 请求审计</li>
 *   <li>CircuitBreakerFilter(600) - 熔断器</li>
 *   <li>ModelCallFilter      (700) - AI 模型调用（含重试）</li>
 *   <li>ParseResponseFilter  (800) - 结构化输出解析</li>
 *   <li>AuditResponseFilter (900) - 响应审计</li>
 * </ol>
 */
public class OrderSummaryService {

    private final GatewayPipeline pipeline;

    public OrderSummaryService(List<GatewayFilter> filters,
                               FallbackAnswerFactory fallbackFactory) {
        this.pipeline = new DefaultGatewayPipeline(filters, fallbackFactory);
    }

    public OrderSummaryResult summarize(OrderSummaryRequest request) {
        return pipeline.execute(request);
    }
}
