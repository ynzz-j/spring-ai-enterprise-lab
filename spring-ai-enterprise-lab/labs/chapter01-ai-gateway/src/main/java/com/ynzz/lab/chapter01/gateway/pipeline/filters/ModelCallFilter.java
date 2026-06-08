package com.ynzz.lab.chapter01.gateway.pipeline.filters;

import com.ynzz.lab.chapter01.common.OrderSummaryResult;
import com.ynzz.lab.chapter01.gateway.model.ModelClient;
import com.ynzz.lab.chapter01.gateway.pipeline.GatewayContext;
import com.ynzz.lab.chapter01.gateway.pipeline.GatewayFilter;
import com.ynzz.lab.chapter01.gateway.pipeline.GatewayChain;

/**
 * AI 模型调用 Filter（执行顺序：700）。
 *
 * <p>这是责任链的核心 Filter，负责调用 AI 模型生成订单摘要。
 *
 * <p>模型调用失败时会做少量重试；全部失败后交给 Pipeline 顶层降级。
 */
public class ModelCallFilter implements GatewayFilter {

    private final ModelClient modelClient;
    private final int maxRetries;       // 最大重试次数
    private final long baseBackoffMs;   // 基础退避时间（毫秒）

    public ModelCallFilter(ModelClient modelClient) {
        this(modelClient, 3, 1000L);  // 默认：最多 3 次重试，基础退避 1 秒
    }

    public ModelCallFilter(ModelClient modelClient, int maxRetries, long baseBackoffMs) {
        this.modelClient = modelClient;
        this.maxRetries = maxRetries;
        this.baseBackoffMs = baseBackoffMs;
    }

    @Override
    public void doFilter(GatewayContext context, GatewayChain chain) {
        String orderId = context.getOrderId();
        String maskedText = context.getMaskedText();

        // 带重试的 AI 模型调用
        OrderSummaryResult result = callWithRetry(orderId, maskedText);

        // 将模型结果写入 Context
        context.setParsedResult(result);
        context.setModelResponse(result.toJson());

        // 继续执行下一个 Filter（ParseResponseFilter）
        chain.doFilter(context);
    }

    private OrderSummaryResult callWithRetry(String orderId, String maskedText) {
        Exception lastException = null;

        // 第 0 次（原调用）
        try {
            return modelClient.summarize(orderId, maskedText);
        } catch (Exception e) {
            lastException = e;
        }

        // 第 1~N 次（重试）
        for (int attempt = 1; attempt <= maxRetries; attempt++) {
            try {
                // 指数退避：第 1 次 1 秒，第 2 次 2 秒，第 3 次 4 秒
                long waitMs = baseBackoffMs * (1L << (attempt - 1));
                Thread.sleep(waitMs);

                return modelClient.summarize(orderId, maskedText);
            } catch (InterruptedException ie) {
                Thread.currentThread().interrupt();
                throw new RuntimeException("Model call retry interrupted", ie);
            } catch (Exception e) {
                lastException = e;
            }
        }

        // 全部重试失败
        throw new RuntimeException(
                "Model call failed after " + maxRetries + " retries. Last error: "
                        + (lastException != null ? lastException.getMessage() : "unknown"),
                lastException);
    }
}
