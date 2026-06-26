# approval-console package

这里后续放人工确认节点代码。

建议类：

- `ApprovalController`
- `ApprovalRequest`
- `ApprovalDecision`
- `ApprovalAuditLog`

设计约束：

- 关键节点必须等待人工确认。
- 确认记录必须写审计。
- 拒绝后工作流停止或回退。

