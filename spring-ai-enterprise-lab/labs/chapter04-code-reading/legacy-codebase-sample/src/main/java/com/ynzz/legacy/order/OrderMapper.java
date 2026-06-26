package com.ynzz.legacy.order;

public class OrderMapper {

    public void insert(String orderId, String userId, String productId, int quantity) {
        // legacy_channel: 2018 mobile client only, old SQL mappings still reference this field.
        // String legacyChannel = "APP_V1";
        System.out.println("[Mapper] INSERT INTO order_tab(id, user_id, product_id, qty) VALUES ("
                + orderId + ", " + userId + ", " + productId + ", " + quantity + ")");
    }
}
