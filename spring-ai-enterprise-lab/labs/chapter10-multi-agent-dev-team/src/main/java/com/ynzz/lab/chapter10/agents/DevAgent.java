package com.ynzz.lab.chapter10.agents;

import com.ynzz.lab.chapter10.common.AgentContribution;
import com.ynzz.lab.chapter10.common.DevTaskRequest;

public interface DevAgent {
    AgentContribution contribute(DevTaskRequest request);
}

