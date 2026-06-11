# mcp-client-agent package

这里后续放 MCP Client Agent 代码。

建议类：

- `McpClientAgentController`
- `ToolCallingAgent`
- `ToolSelectionPromptBuilder`
- `ToolCallTrace`

设计约束：

- Agent 只能通过 MCP Client 调用工具。
- Agent 输出要说明调用了哪些工具。
- 遇到写操作意图时必须拒绝或转人工。

