# Chapter 02 - MCP 工具中心

## 本讲目标

把 Java 8 老系统已有 API 包装成可审计、可限权的 MCP Tool：

```text
老系统 API 不直接暴露给模型，而是由 MCP Tool Server 做权限、参数和返回值控制。
```

## 模块结构

```text
chapter02-mcp-tool-center
├── legacy-api-provider
│   └── 模拟订单、客户、库存只读 API
├── mcp-tool-server
│   └── 把老系统 API 包装成 MCP Tool
├── mcp-client-agent
│   └── AI Agent 通过 MCP Client 调用工具
├── sample-data
│   └── 工具定义和调用样例
└── api.http
```

## 演示流程

```text
1. 老系统提供只读订单 API
2. MCP Tool Server 包装 queryOrder / queryCustomer / checkInventory
3. Agent 根据用户问题选择工具
4. Tool Server 做权限校验和返回值脱敏
5. Agent 汇总工具结果
```

## 本地运行

当前目录已经提供 Maven 可运行的基础 Lab，使用 Spring Boot 作为启动入口。默认不调用旁路模型和真实 MCP Server，先验证企业工具调用边界。

```powershell
.\compile-and-run.ps1
```

如果设置 `AI_CENTER_BASE_URL=http://localhost:18080`，本章会调用 `ai-center-mvp` 的 `/api/mvp/tool-intent`，让 Spring AI 辅助识别读查询/写意图/订单号。

注意：写操作是否拒绝仍由本章 `ToolPermissionPolicy` 这类确定性策略控制，模型不能越过策略层。

可观察结果：

- 正常查询会调用 `queryOrder` 只读工具。
- 工具调用会写审计日志。
- 工具返回中的手机号和地址会脱敏。
- 修改订单状态这类写意图会被拒绝，不进入老系统 API。
- 输出中的 `toolCalls=[]` 可用于观察写操作没有穿透到老系统。

## API

```http
POST /ai/tools/ask
```

输入：

```json
{
  "tenantId": "demo",
  "operatorId": "u1001",
  "question": "帮我查询 O202606050001 这个订单现在有什么异常"
}
```

## 企业边界

- 默认只开放只读 Tool。
- Tool 参数必须校验。
- Tool 返回值必须脱敏。
- Tool 调用必须写审计日志。
- 不允许 Agent 直接绕过 Tool Server 访问老系统。

## 文章反写角度

MCP 不应该被讲成“把生产权限交给 AI”。

更适合的文章主线是：

```text
AI 想调用老系统能力
        ↓
先把 API 包装成受控工具
        ↓
默认只读、调用前授权、调用中审计、调用后脱敏
```
