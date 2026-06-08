package com.ynzz.lab.chapter01.gateway.pipeline.filters;

import com.ynzz.lab.chapter01.common.OrderSummaryResult;
import com.ynzz.lab.chapter01.gateway.pipeline.GatewayContext;
import com.ynzz.lab.chapter01.gateway.pipeline.GatewayFilter;
import com.ynzz.lab.chapter01.gateway.pipeline.GatewayChain;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * 幂等校验 Filter（执行顺序：200）。
 *
 * <p>架构决策：为什么需要幂等？
 * <ul>
 *   <li>防止用户重复点击（如"提交订单"按钮被快速点击 2 次）</li>
 *   <li>防止网络超时重试（客户端自动重试，导致重复 AI 调用）</li>
 *   <li>幂等缓存避免重复计费（AI 调用是按 Token 计费的）</li>
 * </ul>
 *
 * <p>幂等规则：
 * <ul>
 *   <li>相同 orderId + operatorId 在 {@code IDEMPOTENCY_WINDOW_MS} 内重复请求</li>
 *   <li>直接返回缓存的上次结果，不再执行后续 Filter</li>
 *   <li>过期后缓存自动失效，重新执行责任链</li>
 * </ul>
 *
 * <p>缓存写入时机：在 {@code chain.doFilter(context)} 执行完毕后，
 * 从 {@code context.getResult()} 取出最终结果并写入缓存。
 * （责任链是同步调用的，所以 doFilter 返回时后续 Filter 已全部执行完毕。）
 */
public class IdempotencyFilter implements GatewayFilter {

    // 幂等窗口：60 秒
    private static final long IDEMPOTENCY_WINDOW_MS = 60 * 1000L;

    // 幂等缓存：key = orderId + ":" + operatorId
    private final ConcurrentMap<String, CacheEntry> cache = new ConcurrentHashMap<>();

    @Override
    public void doFilter(GatewayContext context, GatewayChain chain) {
        String orderId = context.getOrderId();
        String operatorId = context.getOperatorId();
        String key = orderId + ":" + operatorId;

        // 1. 检查缓存
        CacheEntry cached = cache.get(key);
        long now = System.currentTimeMillis();

        if (cached != null && (now - cached.timestamp) < IDEMPOTENCY_WINDOW_MS) {
            // 命中幂等缓存，直接返回上次结果（短路责任链）
            context.setResult(cached.result);
            return;
        }

        // 2. 未命中，继续执行责任链（同步阻塞）
        chain.doFilter(context);

        // 3. 责任链执行完毕，从 context 取出最终结果，写入缓存
        OrderSummaryResult result = context.getResult();
        if (result != null) {
            cache.put(key, new CacheEntry(now, result));
        }

        // 4. 清理过期缓存（简单实现：每次请求随机清理 1% 的过期条目）
        if (Math.random() < 0.01) {
            cleanExpiredCache(now);
        }
    }

    /**
     * 清理过期缓存。
     */
    private void cleanExpiredCache(long now) {
        for (String key : cache.keySet()) {
            CacheEntry entry = cache.get(key);
            if (entry != null && (now - entry.timestamp) >= IDEMPOTENCY_WINDOW_MS) {
                cache.remove(key);  // 过期，删除
            }
        }
    }

    /**
     * 缓存条目。
     */
    private static class CacheEntry {
        final long timestamp;            // 写入时间（毫秒）
        final OrderSummaryResult result;  // 上次结果

        CacheEntry(long timestamp, OrderSummaryResult result) {
            this.timestamp = timestamp;
            this.result = result;
        }
    }
}
