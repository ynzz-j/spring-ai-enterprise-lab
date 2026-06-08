package com.ynzz.lab.chapter01.gateway.pipeline.filters;

import com.ynzz.lab.chapter01.common.OrderSummaryResult;
import com.ynzz.lab.chapter01.gateway.pipeline.GatewayContext;
import com.ynzz.lab.chapter01.gateway.pipeline.GatewayFilter;
import com.ynzz.lab.chapter01.gateway.pipeline.GatewayChain;

/**
 * 结构化输出解析 Filter（执行顺序：800）。
 *
 * <p>从 {@link GatewayContext#getParsedResult()} 读取模型返回结果，做解析校验。
 *
 * <p>校验内容：
 * <ul>
 *   <li>summary 非空（AI 模型返回了有效摘要）</li>
 *   <li>riskLevel 合法（不合法时降级为 UNKNOWN）</li>
 *   <li>suggestedActions 非空（为空时给默认建议）</li>
 * </ul>
 *
 * <p>校验失败时抛出 {@link RuntimeException}，由 Pipeline 顶层捕获并走降级。
 *
 * <p>这里把“调用”和“校验”分开，方便读者看清 AI 输出进入业务前还要过一道边界。
 */
public class ParseResponseFilter implements GatewayFilter {

    @Override
    public void doFilter(GatewayContext context, GatewayChain chain) {
        OrderSummaryResult parsed = context.getParsedResult();

        // 1. 校验 parsedResult 非空
        if (parsed == null) {
            throw new RuntimeException("AI 模型返回结果为空");
        }

        // 2. 校验 summary 非空
        if (parsed.getSummary() == null || parsed.getSummary().trim().isEmpty()) {
            throw new RuntimeException("AI 模型返回 summary 为空");
        }

        // 3. 校验 riskLevel
        String riskLevel = parsed.getRiskLevel();
        if (riskLevel == null) {
            riskLevel = "UNKNOWN";
        }

        // 4. 使用已有的 suggestedActions（ModelCallFilter 已设置）
        java.util.List<String> suggestedActions = parsed.getSuggestedActions();
        if (suggestedActions == null || suggestedActions.isEmpty()) {
            // 默认建议
            suggestedActions = java.util.Arrays.asList("人工审核订单信息");
        }

        // 5. 构建最终结果（带上脱敏字段列表和 fallback=false）
        OrderSummaryResult finalResult = new OrderSummaryResult(
                parsed.getOrderId(),
                parsed.getSummary(),
                riskLevel,
                suggestedActions,
                false,  // fallback = false（不是降级结果）
                context.getMaskedFields()
        );

        context.setResult(finalResult);

        // 继续执行下一个 Filter（AuditResponseFilter）
        chain.doFilter(context);
    }
}
