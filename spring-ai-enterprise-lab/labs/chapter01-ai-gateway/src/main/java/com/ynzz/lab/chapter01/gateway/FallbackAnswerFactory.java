package com.ynzz.lab.chapter01.gateway;

import com.ynzz.lab.chapter01.common.OrderSummaryResult;

import java.util.Arrays;
import java.util.List;

/**
 * 第 1 讲最小降级答案工厂。
 */
public class FallbackAnswerFactory {
    private static final List<String> DEFAULT_ACTIONS =
            Arrays.asList("查询订单状态", "联系相关业务负责人", "向客户同步人工处理进度");

    public OrderSummaryResult orderSummaryFallback(String orderId,
                                                  List<String> maskedFields,
                                                  String reason) {
        return new OrderSummaryResult(
                orderId,
                "AI 能力暂时不可用，请按标准客服流程人工处理该订单。",
                "UNKNOWN",
                DEFAULT_ACTIONS,
                true,
                maskedFields != null ? maskedFields : Arrays.asList());
    }
}
