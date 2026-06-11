package com.ynzz.lab.chapter02.agent;

import com.ynzz.lab.chapter02.common.ToolAskRequest;
import com.ynzz.lab.chapter02.common.ToolAskResult;
import com.ynzz.lab.chapter02.mcp.OrderQueryTool;
import com.ynzz.lab.chapter02.mcp.ToolPermissionPolicy;
import com.ynzz.lab.chapter02.mcp.ToolResultMasker;

import java.util.Arrays;
import java.util.Collections;

public class ToolCallingAgent {
    private final ToolPermissionPolicy permissionPolicy;
    private final OrderQueryTool orderQueryTool;
    private final ToolIntentClient toolIntentClient;

    public ToolCallingAgent(ToolPermissionPolicy permissionPolicy, OrderQueryTool orderQueryTool) {
        this(permissionPolicy, orderQueryTool, new LocalToolIntentClient(permissionPolicy));
    }

    public ToolCallingAgent(ToolPermissionPolicy permissionPolicy,
                            OrderQueryTool orderQueryTool,
                            ToolIntentClient toolIntentClient) {
        this.permissionPolicy = permissionPolicy;
        this.orderQueryTool = orderQueryTool;
        this.toolIntentClient = toolIntentClient;
    }

    public ToolAskResult ask(ToolAskRequest request) {
        ToolIntent intent = toolIntentClient.inspect(request);
        if (permissionPolicy.isWriteIntent(request.getQuestion())) {
            return new ToolAskResult(
                    "该问题包含写操作意图，AI 不能直接修改老系统订单状态，请转人工审批。",
                    true,
                    "WRITE_INTENT_REJECTED",
                    Collections.<String>emptyList(),
                    Collections.<String>emptyList());
        }

        String orderId = intent.getOrderId();
        ToolResultMasker.MaskedOrder order = orderQueryTool.query(
                request.getTenantId(),
                request.getOperatorId(),
                orderId);

        String answer = "订单 " + order.getOrderId()
                + " 当前状态为 " + order.getStatus()
                + "，异常原因：" + order.getAbnormalReason()
                + "。客户手机号和地址已脱敏。";

        return new ToolAskResult(
                answer,
                false,
                "ALLOW_READ_ONLY_TOOL",
                Arrays.asList("queryOrder"),
                order.getMaskedFields());
    }
}
