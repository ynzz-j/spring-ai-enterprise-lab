# workflow-runtime-demo package

这里后续放工作流运行时代码。

建议类：

- `WorkflowDefinition`
- `WorkflowNode`
- `WorkflowRunner`
- `WorkflowStateRepository`
- `WorkflowSnapshot`
- `WorkflowRetryPolicy`

设计约束：

- 节点输入输出必须保存。
- 节点失败可以重试。
- 工作流可以从上一个成功节点恢复。

