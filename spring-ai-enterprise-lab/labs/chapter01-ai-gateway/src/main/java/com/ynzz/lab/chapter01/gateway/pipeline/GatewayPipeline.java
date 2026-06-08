package com.ynzz.lab.chapter01.gateway.pipeline;

import com.ynzz.lab.chapter01.common.OrderSummaryRequest;
import com.ynzz.lab.chapter01.common.OrderSummaryResult;

/**
 * AI Gateway 处理责任链的主入口。
 *
 * <p>架构决策：为什么用责任链而不是线性代码？
 * <ul>
 *   <li>每个处理步骤独立、可测试、可插拔</li>
 *   <li>新增能力 = 新增 Filter，不改主干流程</li>
 *   <li>执行顺序由 FilterOrderConstants 控制，清晰可维护</li>
 * </ul>
 *
 * <p>使用方式：
 * <pre>{@code
 * GatewayPipeline pipeline = new DefaultGatewayPipeline(filters);
 * OrderSummaryResult result = pipeline.execute(request);
 * }</pre>
 */
public interface GatewayPipeline {

    /**
     * 执行完整的处理责任链。
     *
     * @param request 原始请求
     * @return 处理结果（含摘要、风险等级、建议动作）
     */
    OrderSummaryResult execute(OrderSummaryRequest request);
}
