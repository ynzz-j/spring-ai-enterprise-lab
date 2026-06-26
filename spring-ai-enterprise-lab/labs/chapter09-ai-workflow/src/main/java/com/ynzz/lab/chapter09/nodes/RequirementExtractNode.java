package com.ynzz.lab.chapter09.nodes;

import com.ynzz.lab.chapter09.common.WorkflowNodeSnapshot;

public class RequirementExtractNode {
    public WorkflowNodeSnapshot run(String requirementText) {
        String output = "需求点：订单超过 48 小时未发货时触发延迟预警，并提醒客服介入。";
        return new WorkflowNodeSnapshot("requirement-extract", "SUCCESS", requirementText, output);
    }
}

