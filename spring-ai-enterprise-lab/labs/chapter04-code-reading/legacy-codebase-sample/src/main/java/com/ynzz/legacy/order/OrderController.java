package com.ynzz.legacy.order;

public class OrderController {

    private final OrderService orderService = new OrderService();

    public String createOrder(String userId, String productId, int quantity, int clientVersion) {
        return orderService.createOrder(userId, productId, quantity, clientVersion);
    }
}
