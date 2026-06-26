package com.ynzz.lab.chapter09.nodes;

import com.ynzz.lab.chapter09.common.WorkflowNodeSnapshot;

public class ReleaseChecklistNode {
    public WorkflowNodeSnapshot run(String testcaseOutput) {
        String output = "发布检查：灰度租户、SQL 索引、告警阈值、客服通知模板、回滚开关。";
        return new WorkflowNodeSnapshot("release-checklist", "SUCCESS", testcaseOutput, output);
    }
}

