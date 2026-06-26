package com.ynzz.lab.chapter10.agents;

import com.ynzz.lab.chapter10.common.AgentContribution;
import com.ynzz.lab.chapter10.common.DevTaskRequest;

public class PlannerAgent implements DevAgent {
    public AgentContribution contribute(DevTaskRequest request) {
        return new AgentContribution(
                "Planner",
                "{"
                        + "\"taskBreakdown\": [\"订单查询：找出超过 48 小时未发货的订单\", \"延迟判断：计算订单创建时间到当前的小时差\", \"客服提醒：调用通知接口推送预警\", \"重复提醒幂等：同一订单不重复提醒\", \"审计记录：预警触发记录可追溯\"], "
                        + "\"preConditions\": [\"确认订单状态字段定义（待发货/已发货）\", \"确认客服通知渠道（站内信/邮件/短信）\"], "
                        + "\"affectedModules\": [\"legacy-order 模块的 OrderService\", \"通知模块的 NotificationService\"], "
                        + "\"clarifyItems\": [\"延迟阈值是否所有租户固定为 48 小时\", \"客服通知渠道是否已有统一服务\"]"
                        + "}",
                "requiresClarification=true");
    }
}
