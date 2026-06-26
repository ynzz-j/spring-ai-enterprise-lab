# Chapter 08 - Browser Test Agent

## 本讲目标

让 AI 辅助测试 Java 8 老后台页面：自动打开测试环境、填写表单、截图并生成测试报告。

## 模块结构

```text
chapter08-browser-test-agent
├── legacy-admin-ui
│   └── 模拟老系统后台页面
├── browser-agent-service
│   └── 浏览器任务规划和执行
├── test-report-generator
│   └── 截图、步骤和结果报告
├── sample-data
│   └── 测试任务样例
└── api.http
```

## 演示流程

```text
1. 用户提交测试任务
2. Browser Agent 生成操作计划
3. 人工确认计划
4. Agent 在测试环境执行点击、填写、截图
5. 生成测试报告
```

## 本地运行

当前目录提供 Java 8 纯 Stub 版本，不启动真实浏览器，先验证 Browser Agent 的安全流程。

```powershell
.\compile-and-run.ps1
```

可观察结果：

- 测试环境订单搜索任务会生成 `plan-001`。
- 计划要求人工确认后才能执行。
- 执行结果会输出模拟截图路径。
- 生产环境目标会被 `ONLY_TEST_ENVIRONMENT_ALLOWED` 拦截。
- 删除、审批、支付等高风险任务会被 `HIGH_RISK_ACTION_NOT_ALLOWED` 拦截。

## API

```http
POST /ai/browser/test-plan
POST /ai/browser/test-runs
```

## 企业边界

- 只能访问测试环境。
- 不允许真实支付、删除、审批等高风险操作。
- 操作计划必须先确认。
- 每一步都截图留证。
- 失败步骤必须停止并输出原因。

## 文章反写角度

Browser Agent 很适合做视频效果，但文章必须强调它的安全边界：适合测试和辅助操作，不适合无监督控制生产后台。
