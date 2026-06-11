# mcp-tool-server package

这里后续放 MCP Tool Server 代码。

建议类：

- `OrderQueryTool`
- `CustomerProfileTool`
- `InventoryCheckTool`
- `ToolPermissionPolicy`
- `ToolArgumentValidator`
- `ToolResultMasker`
- `ToolAuditInterceptor`

设计约束：

- Tool 默认只读。
- Tool 入参必须校验。
- Tool 返回必须脱敏。
- Tool 调用必须审计。

