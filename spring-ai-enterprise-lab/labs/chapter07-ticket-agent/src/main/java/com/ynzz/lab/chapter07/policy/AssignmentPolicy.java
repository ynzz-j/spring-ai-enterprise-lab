package com.ynzz.lab.chapter07.policy;

public class AssignmentPolicy {
    public AssigneeRecommendation recommend(String category, String priority) {
        if ("PAYMENT".equals(category)) {
            return new AssigneeRecommendation(
                    "pay-team-lisi",
                    "支付团队值班人员，具备支付回调和订单状态对账经验");
        }
        if ("ORDER_DELIVERY".equals(category)) {
            return new AssigneeRecommendation(
                    "order-team-zhangsan",
                    "订单履约团队值班人员，熟悉仓库出库和物流状态排查。");
        }
        return new AssigneeRecommendation(
                "service-desk",
                "通用服务台先接收，确认分类后再转派。");
    }
}
