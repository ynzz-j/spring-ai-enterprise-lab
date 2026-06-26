package com.ynzz.lab.chapter10.agents;

import com.ynzz.lab.chapter10.common.AgentContribution;
import com.ynzz.lab.chapter10.common.DevTaskRequest;

public class ArchitectAgent implements DevAgent {
    public AgentContribution contribute(DevTaskRequest request) {
        return new AgentContribution(
                "Architect",
                "{"
                        + "\"accessMode\": \"旁路（AI 能力中心生成预警解释文案，规则判断仍在老系统）\", "
                        + "\"moduleBoundaries\": {"
                        + "\"legacy-order\": \"负责订单查询、48 小时判断、预警记录写入（Java 8）\", "
                        + "\"ai-capability-center\": \"接收订单摘要，生成预警解释文案和客服建议话术（Spring AI）\", "
                        + "\"notification-service\": \"接收预警事件，调用客服通知渠道（不改动）\""
                        + "}, "
                        + "\"responsibilitySplit\": \"老系统保持 Java 8 规则引擎；AI 能力中心只做文本生成；两系统通过 MQ/HTTP 解耦\", "
                        + "\"riskNotes\": [\"定时任务扫描频率需可配置\", \"AI 能力中心不可用时的降级策略需明确\"]"
                        + "}",
                "bypassArchitecture=true");
    }
}
