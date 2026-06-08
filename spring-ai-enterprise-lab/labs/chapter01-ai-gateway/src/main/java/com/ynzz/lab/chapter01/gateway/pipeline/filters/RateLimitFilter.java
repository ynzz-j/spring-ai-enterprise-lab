package com.ynzz.lab.chapter01.gateway.pipeline.filters;

import com.ynzz.lab.chapter01.gateway.pipeline.GatewayContext;
import com.ynzz.lab.chapter01.gateway.pipeline.GatewayFilter;
import com.ynzz.lab.chapter01.gateway.pipeline.GatewayChain;
import com.ynzz.lab.chapter01.gateway.RateLimitService;

/**
 * 限流 Filter（执行顺序：300）。
 *
 * <p>调用 {@link RateLimitService#check(String, String)} 进行限流校验。
 * 如果超限，抛出 {@link IllegalStateException}，由 Pipeline 顶层捕获并走降级。
 *
 * <p>限流是前置校验，放在模型调用之前，避免异常流量直接打到 AI 能力。
 */
public class RateLimitFilter implements GatewayFilter {

    private final RateLimitService rateLimitService;

    public RateLimitFilter(RateLimitService rateLimitService) {
        this.rateLimitService = rateLimitService;
    }

    @Override
    public void doFilter(GatewayContext context, GatewayChain chain) {
        String tenantId = context.getTenantId();
        String operatorId = context.getOperatorId();

        // 调用限流服务（超限时抛出 IllegalStateException）
        rateLimitService.check(tenantId, operatorId);

        // 限流通过，继续执行下一个 Filter
        chain.doFilter(context);
    }
}
