# sql-safety-engine package

这里后续放 SQL 安全校验代码。

建议类：

- `SqlSafetyEngine`
- `SqlParseResult`
- `SqlPolicy`
- `TableWhitelist`
- `ColumnWhitelist`
- `SensitiveColumnPolicy`
- `LimitEnforcer`
- `SqlTimeoutPolicy`

设计约束：

- 只允许 `SELECT`。
- 表名和字段名必须在白名单中。
- 敏感字段默认拒绝。
- SQL 必须带 `LIMIT` 或由 `LimitEnforcer` 自动补齐。
- 所有拒绝都要返回明确原因，方便文章截图展示。

