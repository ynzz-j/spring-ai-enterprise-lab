package com.ynzz.lab.chapter09.runtime;

import com.ynzz.lab.chapter09.common.WorkflowRun;

public interface WorkflowStateRepository {
    void save(WorkflowRun run);

    WorkflowRun find(String workflowId);
}
