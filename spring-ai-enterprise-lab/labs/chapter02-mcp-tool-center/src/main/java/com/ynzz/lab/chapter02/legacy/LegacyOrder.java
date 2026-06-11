package com.ynzz.lab.chapter02.legacy;

public class LegacyOrder {
    private final String orderId;
    private final String status;
    private final String abnormalReason;
    private final String customerMobile;
    private final String address;

    public LegacyOrder(String orderId, String status, String abnormalReason,
                       String customerMobile, String address) {
        this.orderId = orderId;
        this.status = status;
        this.abnormalReason = abnormalReason;
        this.customerMobile = customerMobile;
        this.address = address;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getStatus() {
        return status;
    }

    public String getAbnormalReason() {
        return abnormalReason;
    }

    public String getCustomerMobile() {
        return customerMobile;
    }

    public String getAddress() {
        return address;
    }
}

