package com.ynzz.lab.chapter03.agent;

import java.util.List;

public class SqlResultSummarizer {
    public String summarize(String question, List<String> rows) {
        if (rows.isEmpty()) {
            return "没有查询到匹配数据。";
        }

        if (question.contains("退款")) {
            return "本月退款订单数量为 23。";
        }

        if (question.contains("销售额")) {
            return "本月销售额最高的商品是 AI 开发训练营，销售额 128000 元。";
        }

        return "查询已完成，请结合返回行数据进一步确认。";
    }
}

