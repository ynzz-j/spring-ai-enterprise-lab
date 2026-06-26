package com.ynzz.lab.chapter09.nodes;

import com.ynzz.lab.chapter09.common.WorkflowNodeSnapshot;

public class ApiDesignNode {
    public WorkflowNodeSnapshot run(String requirementSummary) {
        String output = "POST /api/orders/delay-alerts，输入 thresholdHours=48，输出待介入订单列表。";
        return new WorkflowNodeSnapshot("api-design", "WAITING_APPROVAL", requirementSummary, output);
    }
}

