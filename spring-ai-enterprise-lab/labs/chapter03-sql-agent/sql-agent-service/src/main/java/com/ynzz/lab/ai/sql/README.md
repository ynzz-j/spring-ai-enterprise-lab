# sql-agent-service package

这里后续放 SQL Agent 服务代码。

建议类：

- `SqlAgentController`
- `SchemaSnapshotService`
- `SqlPromptBuilder`
- `SqlGenerateService`
- `ReadOnlySqlExecutor`
- `SqlResultSummarizer`
- `SqlQueryRequest`
- `SqlQueryResponse`

设计约束：

- 模型只生成候选 SQL。
- 候选 SQL 必须先交给 `sql-safety-engine`。
- 执行器只能连接只读数据源。
- 输出必须包含 SQL、安全结论、摘要和原始行数据。

