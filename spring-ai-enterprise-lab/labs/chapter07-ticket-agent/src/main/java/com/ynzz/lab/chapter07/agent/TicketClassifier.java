package com.ynzz.lab.chapter07.agent;

public class TicketClassifier {
    public String classify(String title, String content) {
        String text = title + " " + content;
        if (text.contains("支付") || text.contains("未支付")) {
            return "PAYMENT";
        }
        if (text.contains("发货") || text.contains("物流") || text.contains("延迟")) {
            return "ORDER_DELIVERY";
        }
        return "GENERAL";
    }
}

