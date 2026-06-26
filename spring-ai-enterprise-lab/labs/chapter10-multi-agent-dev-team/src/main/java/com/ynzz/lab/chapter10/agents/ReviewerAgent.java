package com.ynzz.lab.chapter10.agents;

import com.ynzz.lab.chapter10.common.AgentContribution;
import com.ynzz.lab.chapter10.common.DevTaskRequest;

public class ReviewerAgent implements DevAgent {
    public AgentContribution contribute(DevTaskRequest request) {
        return new AgentContribution(
                "Reviewer",
                "{"
                        + "\"riskList\": ["
                        + "{\"description\": \"Coder 建议新增 DelayAlertService 在 legacy-order 模块，但 Architect 建议旁路，需确认职责边界\", \"severity\": \"高\", \"sourceAgent\": \"Coder\", \"suggestedAction\": \"人工确认是否允许在老系统新增 Service\"}, "
                        + "{\"description\": \"定时任务缺少幂等键，可能重复推送预警\", \"severity\": \"中\", \"sourceAgent\": \"Coder\", \"suggestedAction\": \"参考历史评审结论：定时任务必须有幂等键\"}, "
                        + "{\"description\": \"AI Gateway 调用未配置超时和重试\", \"severity\": \"中\", \"sourceAgent\": \"Coder\", \"suggestedAction\": \"补充 Resilience4j 配置\"}"
                        + "], "
                        + "\"reviewFocus\": [\"幂等设计\", \"多租户差异处理\", \"通知失败重试机制\", \"生产数据库变更（新增表/字段）需 DBA 审批\"], "
                        + "\"preDeploymentItems\": [\"补充预警去重表（delay_alert_log）\", \"配置 AI Gateway 超时时间\", \"通知模板需运营确认\"], "
                        + "\"approved\": false"
                        + "}",
                "requiresHumanConfirmation=true");
    }
}
