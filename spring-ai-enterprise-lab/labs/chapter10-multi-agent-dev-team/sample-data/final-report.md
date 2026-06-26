# 多 Agent 开发报告

## 需求摘要

新增订单延迟预警功能：超过 48 小时未发货时提醒客服介入。

## Agent 输出

- Planner Agent：`taskBreakdown` 包含订单查询、48 小时延迟判断、客服提醒、重复提醒幂等、审计记录；`clarifyItems` 包含延迟阈值和客服通知渠道。
- Architect Agent：`accessMode=旁路`；`moduleBoundaries` 区分 `legacy-order`、`ai-capability-center`、`notification-service`。
- Coder Agent：`patchSuggestions` 只给 `DelayAlertService.java` 和 `OrderMapper.xml` 的建议，不直接修改仓库。
- Tester Agent：`testCases` 覆盖正常、边界、异常、幂等、通知失败重试。
- Reviewer Agent：`riskList` 标记旁路职责边界、幂等键、AI Gateway 超时重试等风险，`approved=false`。

## 冲突

- Architect 建议旁路 AI 能力中心，Coder 建议新增老系统 DelayAlertService：需要确认规则判断和 AI 文案生成的职责边界。
- 所有 Patch 建议必须停留在代码审查阶段，不能由 Agent 直接执行数据库变更。

## 不确定项

- 客服通知渠道是否已有统一服务。
- 延迟阈值是否所有租户都固定为 48 小时。

## CodeStep 训练建议

- 定时任务幂等训练。
- Service 层业务规则阅读训练。
- 测试用例边界设计训练。
