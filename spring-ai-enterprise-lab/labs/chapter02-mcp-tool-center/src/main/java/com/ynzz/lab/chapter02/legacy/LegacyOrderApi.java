package com.ynzz.lab.chapter02.legacy;

public class LegacyOrderApi {
    public LegacyOrder queryOrder(String tenantId, String orderId) {
        return new LegacyOrder(
                orderId,
                "DELAYED",
                "仓库尚未确认出库时间",
                "13800000000",
                "上海市浦东新区某某路 100 号");
    }
}

