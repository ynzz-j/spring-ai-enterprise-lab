package com.ynzz.lab.chapter01.gateway.model;

import com.ynzz.lab.chapter01.common.OrderSummaryRequest;
import com.ynzz.lab.chapter01.common.OrderSummaryResult;

/**
 * AI 模型调用抽象接口。
 *
 * <p>第 1 章只保留两个入口：默认本地 Stub，可选 HTTP 调用旁路 AI Center。
 * Gateway 责任链只依赖这个接口，便于演示“老系统不直接关心模型供应商”。
 */
public interface ModelClient {

    /**
     * 对脱敏后的订单文本生成摘要。
     *
     * @param orderId     订单 ID（用于日志追踪）
     * @param maskedText  脱敏后的订单文本
     * @return 结构化摘要结果
     * @throws RuntimeException 当模型调用失败时使用方需处理降级
     */
    OrderSummaryResult summarize(String orderId, String maskedText);

    /**
     * 当前模型是否可用。用于熔断器（CircuitBreaker）判断。
     * Stub 实现可手动设 available=false 演示熔断。
     */
    boolean isAvailable();
}
