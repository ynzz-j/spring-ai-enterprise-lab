# ticket-agent-service package

这里后续放工单 Agent 代码。

建议类：

- `TicketAnalyzeController`
- `TicketSummaryService`
- `TicketClassifier`
- `SlaRiskDetector`
- `AssigneeRecommendTool`
- `TicketAgentResponse`
- `HumanApprovalService`

设计约束：

- AI 输出必须结构化。
- 推荐处理人必须附理由。
- 高风险工单进入人工确认。
- 不能自动派单。

