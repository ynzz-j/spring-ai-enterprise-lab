package com.ynzz.lab.chapter10.orchestrator;

import com.ynzz.lab.chapter10.agents.ArchitectAgent;
import com.ynzz.lab.chapter10.agents.CoderAgent;
import com.ynzz.lab.chapter10.agents.DevAgent;
import com.ynzz.lab.chapter10.agents.PlannerAgent;
import com.ynzz.lab.chapter10.agents.ReviewerAgent;
import com.ynzz.lab.chapter10.agents.TesterAgent;
import com.ynzz.lab.chapter10.common.AgentContribution;
import com.ynzz.lab.chapter10.common.DevTaskRequest;
import com.ynzz.lab.chapter10.common.DevTeamReport;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DevTeamOrchestrator {
    private final List<DevAgent> agents = Arrays.asList(
            new PlannerAgent(),
            new ArchitectAgent(),
            new CoderAgent(),
            new TesterAgent(),
            new ReviewerAgent());
    private final ConflictDetector conflictDetector = new ConflictDetector();

    public DevTeamReport run(DevTaskRequest request) {
        List<AgentContribution> contributions = new ArrayList<AgentContribution>();
        for (DevAgent agent : agents) {
            contributions.add(agent.contribute(request));
        }

        return new DevTeamReport(
                request,
                contributions,
                conflictDetector.detect(request, contributions),
                Arrays.asList("客服通知渠道是否已有统一服务。", "延迟阈值是否所有租户都固定为 48 小时。"),
                Arrays.asList("定时任务幂等训练", "Service 层业务规则阅读训练", "测试用例边界设计训练"),
                true,
                true);
    }
}

