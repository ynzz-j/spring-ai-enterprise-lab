# agent-orchestrator package

这里后续放多 Agent 编排代码。

建议类：

- `AgentOrchestrator`
- `AgentTask`
- `AgentContext`
- `AgentMemoryStore`
- `AgentMessage`
- `ConflictResolver`
- `UncertaintyCollector`

设计约束：

- Agent 之间通过结构化上下文传递信息。
- 冲突和不确定项必须被收集。
- 最终输出需要人工确认。

