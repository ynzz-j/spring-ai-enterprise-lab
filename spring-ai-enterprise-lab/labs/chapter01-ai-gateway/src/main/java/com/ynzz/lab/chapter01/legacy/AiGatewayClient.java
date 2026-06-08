package com.ynzz.lab.chapter01.legacy;

import com.ynzz.lab.chapter01.common.OrderSummaryRequest;
import com.ynzz.lab.chapter01.common.OrderSummaryResult;
import com.ynzz.lab.chapter01.gateway.OrderSummaryService;

/**
 * AI Gateway 客户端（老系统侧）。
 *
 * <p>职责：让老系统只看见一个稳定入口，不关心 AI Gateway 内部怎么调用模型。
 *
 * <p>本章为了保持最小 Demo，先用直接 Java 调用模拟“老系统调用旁路 Gateway”。
 * 重点放在边界设计：老系统少改、AI 只给建议、异常时可降级。
 */
public class AiGatewayClient {

    private final OrderSummaryService orderSummaryService;

    public AiGatewayClient(OrderSummaryService orderSummaryService) {
        this.orderSummaryService = orderSummaryService;
    }

    /**
     * 调用 AI Gateway 生成订单摘要。
     *
     * @param request 订单摘要请求（含订单文本、租户信息等）
     * @return 结构化摘要结果（含风险等级、建议动作、脱敏字段列表）
     */
    public OrderSummaryResult summarizeOrder(OrderSummaryRequest request) {
        return orderSummaryService.summarize(request);
    }
}
