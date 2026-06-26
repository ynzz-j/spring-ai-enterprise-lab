package com.ynzz.lab.chapter10.agents;

import com.ynzz.lab.chapter10.common.AgentContribution;
import com.ynzz.lab.chapter10.common.DevTaskRequest;

public class CoderAgent implements DevAgent {
    public AgentContribution contribute(DevTaskRequest request) {
        return new AgentContribution(
                "Coder",
                "{"
                        + "\"patchSuggestions\": ["
                        + "{\"file\": \"DelayAlertService.java\", \"action\": \"新增\", \"description\": \"定时任务扫描延迟订单，调用 AI Gateway 获取解释文案\"}, "
                        + "{\"file\": \"OrderMapper.xml\", \"action\": \"新增 SQL\", \"description\": \"查询超过 N 小时未发货的订单列表\"}"
                        + "], "
                        + "\"classMethodHints\": [\"DelayAlertService#findDelayedOrders(thresholdHours)\", \"DelayAlertService#pushAlert(order, aiSuggestion)\"], "
                        + "\"dtoSuggestions\": [\"AiGatewayClient.DelayAlertRequest\", \"AiGatewayClient.DelayAlertResponse\"], "
                        + "\"configurationHints\": [\"delay.alert.threshold-hours=48（application.properties）\", \"delay.alert.cron=0 0 * * * ?（每小时执行一次）\"]"
                        + "}",
                "patchSuggestionOnly=true, doesNotModifyRepo=true");
    }
}
