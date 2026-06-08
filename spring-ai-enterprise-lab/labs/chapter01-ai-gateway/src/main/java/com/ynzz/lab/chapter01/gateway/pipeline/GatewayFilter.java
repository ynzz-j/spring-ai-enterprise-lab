package com.ynzz.lab.chapter01.gateway.pipeline;

/**
 * AI Gateway 责任链中的单个处理节点。
 *
 * <p>架构决策：为什么用责任链而不是线性代码？
 * <ul>
 *   <li>每个处理步骤独立、可测试、可插拔</li>
 *   <li>新增能力 = 新增 Filter，不改主干流程</li>
 *   <li>执行顺序由 FilterOrderConstants 控制，清晰可维护</li>
 * </ul>
 *
 * <p>每个 Filter 只做一件事，通过 {@link GatewayContext} 传递中间状态。
 * 调用 {@code chain.doFilter(context)} 将控制权交给下一个 Filter。
 * 如果 Filter 直接返回（不调用 chain），责任链短路。
 */
public interface GatewayFilter {

    /**
     * 执行过滤逻辑。
     *
     * @param context 贯穿整条链的上下文，各 Filter 读写中间状态
     * @param chain   责任链执行器，调用 chain.doFilter(context) 继续执行下一个 Filter
     */
    void doFilter(GatewayContext context, GatewayChain chain);
}
