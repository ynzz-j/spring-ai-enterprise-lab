package com.ynzz.lab.chapter10.orchestrator;

import com.ynzz.lab.chapter10.common.AgentContribution;
import com.ynzz.lab.chapter10.common.DevTaskRequest;

import java.util.ArrayList;
import java.util.List;

public class ConflictDetector {
    public List<String> detect(DevTaskRequest request, List<AgentContribution> contributions) {
        List<String> conflicts = new ArrayList<String>();
        conflicts.add("Architect 建议旁路 AI 能力中心，Coder 建议新增老系统 DelayAlertService：需要确认规则判断和 AI 文案生成的职责边界。");
        if (request.getConstraints().contains("不能直接修改生产数据库")) {
            conflicts.add("所有 Patch 建议必须停留在代码审查阶段，不能由 Agent 直接执行数据库变更。");
        }
        return conflicts;
    }
}

