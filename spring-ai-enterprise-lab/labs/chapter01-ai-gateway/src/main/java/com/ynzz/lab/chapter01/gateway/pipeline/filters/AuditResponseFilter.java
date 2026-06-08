package com.ynzz.lab.chapter01.gateway.pipeline.filters;

import com.ynzz.lab.chapter01.common.OrderSummaryResult;
import com.ynzz.lab.chapter01.gateway.AuditLogService;
import com.ynzz.lab.chapter01.gateway.pipeline.GatewayContext;
import com.ynzz.lab.chapter01.gateway.pipeline.GatewayFilter;
import com.ynzz.lab.chapter01.gateway.pipeline.GatewayChain;

/**
 * 响应审计 Filter（执行顺序：900）。
 *
 * <p>在 AI 调用完成后记录响应信息，用于合规审计和链路追踪。
 *
 * <p>记录内容：
 * <ul>
 *   <li>traceId（链路追踪 ID）</li>
 *   <li>orderId（订单 ID）</li>
 *   <li>riskLevel（风险等级）</li>
 *   <li>fallback（是否降级）</li>
 * </ul>
 *
 * <p>请求审计放在模型调用前，响应审计放在模型调用后，方便定位一次 AI 调用的完整链路。
 */
public class AuditResponseFilter implements GatewayFilter {

    private final AuditLogService auditLogService;

    public AuditResponseFilter(AuditLogService auditLogService) {
        this.auditLogService = auditLogService;
    }

    @Override
    public void doFilter(GatewayContext context, GatewayChain chain) {
        // 先执行后续 Filter（如果有的话）
        chain.doFilter(context);

        // 从最终结果中提取审计信息
        OrderSummaryResult result = context.getResult();
        String traceId = context.getTraceId();
        String tenantId = context.getTenantId();
        String operatorId = context.getOperatorId();
        String orderId = context.getOrderId();

        if (result != null) {
            auditLogService.record(tenantId, operatorId,
                    "ORDER_SUMMARY_RESPONSE",
                    "traceId=" + traceId
                            + ", orderId=" + orderId
                            + ", fallback=" + result.isFallback()
                            + ", riskLevel=" + result.getRiskLevel());
        }
    }
}
