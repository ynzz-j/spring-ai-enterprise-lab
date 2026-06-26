# browser-agent-service package

这里后续放 Browser Agent 代码。

建议类：

- `BrowserPlanController`
- `BrowserRunController`
- `BrowserTaskPlanner`
- `BrowserSafetyPolicy`
- `BrowserTool`
- `PageStateAnalyzer`
- `BrowserActionExecutor`

设计约束：

- 先生成计划，再执行计划。
- 执行前必须确认。
- `BrowserSafetyPolicy` 拦截生产环境和高风险动作。
- 每一步都保留截图或日志。
