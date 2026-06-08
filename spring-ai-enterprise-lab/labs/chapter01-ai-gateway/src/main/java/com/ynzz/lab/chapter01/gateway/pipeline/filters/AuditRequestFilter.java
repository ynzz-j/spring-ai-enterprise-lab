package com.ynzz.lab.chapter01.gateway.pipeline.filters;

import com.ynzz.lab.chapter01.gateway.AuditLogService;
import com.ynzz.lab.chapter01.gateway.pipeline.GatewayContext;
import com.ynzz.lab.chapter01.gateway.pipeline.GatewayFilter;
import com.ynzz.lab.chapter01.gateway.pipeline.GatewayChain;

/**
 * 请求审计 Filter（执行顺序：500）。
 *
 * <p>在 AI 调用之前记录请求信息，用于合规审计和问题排查。
 *
 * <p>记录内容：
 * <ul>
 *   <li>traceId（链路追踪 ID）</li>
 *   <li>tenantId（租户 ID）</li>
 *   <li>operatorId（操作人 ID）</li>
 *   <li>orderId（订单 ID）</li>
 *   <li>maskedFields（被脱敏的字段列表）</li>
 * </ul>
 *
 * <p>请求审计放在模型调用前，响应审计放在模型调用后，方便定位一次 AI 调用的完整链路。
 */
public class AuditRequestFilter implements GatewayFilter {

    private final AuditLogService auditLogService;

    public AuditRequestFilter(AuditLogService auditLogService) {
        this.auditLogService = auditLogService;
    }

    @Override
    public void doFilter(GatewayContext context, GatewayChain chain) {
        String traceId = context.getTraceId();
        String tenantId = context.getTenantId();
        String operatorId = context.getOperatorId();
        String orderId = context.getOrderId();

        // 记录请求审计日志
        auditLogService.record(tenantId, operatorId,
                "ORDER_SUMMARY_REQUEST",
                "traceId=" + traceId
                        + ", orderId=" + orderId
                        + ", maskedFields=" + context.getMaskedFields());

        // 继续执行下一个 Filter
        chain.doFilter(context);
    }
}
