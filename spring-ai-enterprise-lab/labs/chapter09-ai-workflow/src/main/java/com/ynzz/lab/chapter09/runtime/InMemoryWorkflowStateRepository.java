package com.ynzz.lab.chapter09.runtime;

import com.ynzz.lab.chapter09.common.WorkflowRun;

import java.util.HashMap;
import java.util.Map;

public class InMemoryWorkflowStateRepository implements WorkflowStateRepository {
    private final Map<String, WorkflowRun> runs = new HashMap<String, WorkflowRun>();

    public void save(WorkflowRun run) {
        runs.put(run.getWorkflowId(), run);
    }

    public WorkflowRun find(String workflowId) {
        return runs.get(workflowId);
    }
}
