package com.ynzz.lab.chapter03.agent;

import java.util.Arrays;
import java.util.List;

public class ReadOnlySqlExecutor {
    public List<String> execute(String safeSql) {
        String normalized = safeSql.toLowerCase();

        if (normalized.contains("refund_count")) {
            return Arrays.asList("{refund_count=23}");
        }

        if (normalized.contains("sum(amount)")) {
            return Arrays.asList(
                    "{product_name=AI 开发训练营, total_amount=128000}",
                    "{product_name=Spring AI 企业实战课, total_amount=96000}");
        }

        return Arrays.asList("{demo=readonly-result}");
    }
}

