package com.ynzz.lab.chapter09.runtime;

import com.ynzz.lab.chapter09.common.WorkflowNodeSnapshot;
import com.ynzz.lab.chapter09.common.WorkflowRun;
import com.ynzz.lab.chapter09.common.WorkflowStartRequest;
import com.ynzz.lab.chapter09.nodes.ApiDesignNode;
import com.ynzz.lab.chapter09.nodes.ReleaseChecklistNode;
import com.ynzz.lab.chapter09.nodes.RequirementExtractNode;
import com.ynzz.lab.chapter09.nodes.TestcaseGenerateNode;

public class WorkflowRuntime {
    private final RequirementExtractNode requirementExtractNode = new RequirementExtractNode();
    private final ApiDesignNode apiDesignNode = new ApiDesignNode();
    private final TestcaseGenerateNode testcaseGenerateNode = new TestcaseGenerateNode();
    private final ReleaseChecklistNode releaseChecklistNode = new ReleaseChecklistNode();
    private final WorkflowStateRepository stateRepository;

    public WorkflowRuntime() {
        this(new InMemoryWorkflowStateRepository());
    }

    public WorkflowRuntime(WorkflowStateRepository stateRepository) {
        this.stateRepository = stateRepository;
    }

    public WorkflowRun start(WorkflowStartRequest request) {
        WorkflowRun run = new WorkflowRun("wf-001", request.getTenantId(), request.getOperatorId());
        run.addSnapshot(requirementExtractNode.run(request.getRequirementText()));
        run.addSnapshot(apiDesignNode.run(run.lastOutput()));
        run.waitForApproval("api-design");
        run.addUncertainty("客服提醒渠道是短信、企微还是站内信，需要业务确认。");
        stateRepository.save(run);
        return run;
    }

    public void approve(WorkflowRun run, String nodeId, boolean approved, String approvedBy) {
        WorkflowNodeSnapshot snapshot = run.findSnapshot(nodeId);
        if (snapshot == null || !approved) {
            return;
        }
        snapshot.approve(approvedBy);
        run.running();
        run.addSnapshot(testcaseGenerateNode.run(snapshot.getOutput()));
        run.addSnapshot(releaseChecklistNode.run(run.lastOutput()));
        run.complete();
        stateRepository.save(run);
    }

    public WorkflowRun find(String workflowId) {
        return stateRepository.find(workflowId);
    }
}
