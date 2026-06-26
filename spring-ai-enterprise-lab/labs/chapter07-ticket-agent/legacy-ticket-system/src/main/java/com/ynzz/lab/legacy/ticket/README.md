# legacy-ticket-system package

这里后续放 Java 8 老工单系统代码。

建议类：

- `LegacyTicketController`
- `LegacyTicketEventPublisher`
- `TicketCreatedEvent`
- `TicketAssignmentCallback`

设计约束：

- 老系统只负责推送事件和接收人工确认后的派单结果。
- 不直接依赖 AI Agent。
- 不让 AI 直接改工单状态。

