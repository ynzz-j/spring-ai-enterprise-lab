# legacy-api-provider package

这里后续放 Java 8 老系统只读 API。

建议类：

- `LegacyOrderApi`
- `LegacyCustomerApi`
- `LegacyInventoryApi`
- `LegacyApiResponse`

设计约束：

- 只提供查询能力。
- 不感知 MCP。
- 不感知模型。
- 对外暴露稳定业务 API，由 MCP Tool Server 负责包装。

