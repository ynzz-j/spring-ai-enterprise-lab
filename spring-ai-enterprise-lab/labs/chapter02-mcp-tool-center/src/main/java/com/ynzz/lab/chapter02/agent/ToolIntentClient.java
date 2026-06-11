package com.ynzz.lab.chapter02.agent;

import com.ynzz.lab.chapter02.common.ToolAskRequest;

public interface ToolIntentClient {
    ToolIntent inspect(ToolAskRequest request);
}
