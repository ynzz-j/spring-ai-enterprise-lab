package com.ynzz.lab.chapter02.mcp;

import com.ynzz.lab.chapter02.legacy.LegacyOrder;

import java.util.Arrays;
import java.util.List;

public class ToolResultMasker {
    public MaskedOrder mask(LegacyOrder order) {
        return new MaskedOrder(
                order.getOrderId(),
                order.getStatus(),
                order.getAbnormalReason(),
                "1**********",
                "******",
                Arrays.asList("customerMobile", "address"));
    }

    public static class MaskedOrder {
        private final String orderId;
        private final String status;
        private final String abnormalReason;
        private final String customerMobile;
        private final String address;
        private final List<String> maskedFields;

        public MaskedOrder(String orderId, String status, String abnormalReason,
                           String customerMobile, String address, List<String> maskedFields) {
            this.orderId = orderId;
            this.status = status;
            this.abnormalReason = abnormalReason;
            this.customerMobile = customerMobile;
            this.address = address;
            this.maskedFields = maskedFields;
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

        public List<String> getMaskedFields() {
            return maskedFields;
        }
    }
}

