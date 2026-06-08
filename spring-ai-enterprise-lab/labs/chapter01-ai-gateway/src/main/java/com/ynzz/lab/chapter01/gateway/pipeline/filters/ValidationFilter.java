package com.ynzz.lab.chapter01.gateway.pipeline.filters;

import com.ynzz.lab.chapter01.common.OrderSummaryRequest;
import com.ynzz.lab.chapter01.gateway.pipeline.FilterOrderConstants;
import com.ynzz.lab.chapter01.gateway.pipeline.GatewayContext;
import com.ynzz.lab.chapter01.gateway.pipeline.GatewayFilter;
import com.ynzz.lab.chapter01.gateway.pipeline.GatewayChain;

/**
 * 参数校验 Filter（执行顺序：100）。
 *
 * <p>校验内容：
 * <ul>
 *   <li>orderId 非空</li>
 *   <li>orderText 非空</li>
 *   <li>orderText 长度上限 5000 字符（防止 Token 超限）</li>
 *   <li>tenantId 非空</li>
 * </ul>
 *
 * <p>校验失败直接设置 result（短路责任链），不调用 chain.doFilter()。
 */
public class ValidationFilter implements GatewayFilter {

    private static final int MAX_ORDER_TEXT_LENGTH = 5000;

    @Override
    public void doFilter(GatewayContext context, GatewayChain chain) {
        OrderSummaryRequest request = context.getRequest();
        String orderId = request.getOrderId();
        String orderText = request.getOrderText();
        String tenantId = request.getTenantId();

        // 1. orderId 非空
        if (orderId == null || orderId.trim().isEmpty()) {
            setErrorResult(context, "VALIDATION_ERROR", "orderId 不能为空");
            return;  // 短路责任链
        }

        // 2. orderText 非空
        if (orderText == null || orderText.trim().isEmpty()) {
            setErrorResult(context, "VALIDATION_ERROR", "orderText 不能为空");
            return;
        }

        // 3. orderText 长度上限
        if (orderText.length() > MAX_ORDER_TEXT_LENGTH) {
            setErrorResult(context, "VALIDATION_ERROR",
                    "orderText 长度超限（最大 " + MAX_ORDER_TEXT_LENGTH + " 字符）");
            return;
        }

        // 4. tenantId 非空
        if (tenantId == null || tenantId.trim().isEmpty()) {
            setErrorResult(context, "VALIDATION_ERROR", "tenantId 不能为空");
            return;
        }

        // 校验通过，继续执行下一个 Filter
        chain.doFilter(context);
    }

    private void setErrorResult(GatewayContext context, String reason, String message) {
        context.setResult(new com.ynzz.lab.chapter01.common.OrderSummaryResult(
                context.getOrderId(),
                "请求参数错误：" + message,
                "UNKNOWN",
                java.util.Collections.emptyList(),
                true,  // fallback = true
                context.getMaskedFields()
        ));
        context.setException(new IllegalArgumentException(message));
    }
}
