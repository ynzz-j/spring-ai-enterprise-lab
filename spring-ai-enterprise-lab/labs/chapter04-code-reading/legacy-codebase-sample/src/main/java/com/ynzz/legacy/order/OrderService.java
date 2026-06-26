package com.ynzz.legacy.order;

public class OrderService {

    private final OrderMapper orderMapper = new OrderMapper();

    public String createOrder(String userId, String productId, int quantity, int clientVersion) {
        try {
            if (quantity <= 0) {
                throw new IllegalArgumentException("quantity must be positive");
            }

            String normalizedProductId = productId;
            if (clientVersion == 1) {
                normalizedProductId = "LEGACY-" + productId;
            }

            String orderId = "O-" + System.currentTimeMillis();
            orderMapper.insert(orderId, userId, normalizedProductId, quantity);
            return orderId;
        } catch (RuntimeException ex) {
            return "CREATE_ORDER_FAILED";
        }
    }
}
