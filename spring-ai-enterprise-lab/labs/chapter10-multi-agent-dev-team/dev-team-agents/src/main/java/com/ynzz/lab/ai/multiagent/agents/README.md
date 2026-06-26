# dev-team-agents package

这里后续放开发团队 Agent。

建议类：

- `PlannerAgent`
- `ArchitectAgent`
- `CoderAgent`
- `TesterAgent`
- `ReviewerAgent`

设计约束：

- 每个 Agent 只负责自己的角色。
- Coder Agent 输出 Patch 建议，不直接修改代码。
- Reviewer Agent 必须指出风险和缺失测试。

