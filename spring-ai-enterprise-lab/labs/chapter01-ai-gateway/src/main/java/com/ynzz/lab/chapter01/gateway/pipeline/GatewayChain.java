package com.ynzz.lab.chapter01.gateway.pipeline;

import java.util.List;

/**
 * 责任链执行器。持有 Filter 列表和当前执行位置。
 *
 * <p>每次调用 {@link #doFilter(GatewayContext)} 时，
 * 从当前位置取出下一个 Filter 并执行。
 * 所有 Filter 执行完毕后，责任链结束。
 */
public class GatewayChain {

    private final List<GatewayFilter> filters;
    private int pos = 0;

    public GatewayChain(List<GatewayFilter> filters) {
        this.filters = filters;
    }

    /**
     * 执行下一个 Filter。
     * 如果所有 Filter 已执行完毕，责任链结束（直接返回）。
     */
    public void doFilter(GatewayContext context) {
        if (pos >= filters.size()) {
            // 所有 Filter 执行完毕，Pipeline 结束
            return;
        }
        GatewayFilter next = filters.get(pos++);
        next.doFilter(context, this);
    }
}
