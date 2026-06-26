package com.ynzz.lab.chapter09.nodes;

import com.ynzz.lab.chapter09.common.WorkflowNodeSnapshot;

public class TestcaseGenerateNode {
    public WorkflowNodeSnapshot run(String apiDesign) {
        String output = "测试用例：48 小时边界、未发货订单、已发货订单、重复提醒幂等、客服可见性。";
        return new WorkflowNodeSnapshot("testcase-generate", "SUCCESS", apiDesign, output);
    }
}

