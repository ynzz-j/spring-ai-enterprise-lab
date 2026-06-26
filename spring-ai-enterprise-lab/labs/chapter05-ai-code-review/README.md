# Chapter 05 - AI Code Review 防线

## 本讲目标

给 Java 8 老项目加一道 AI Code Review 防线：

```text
提交代码后，系统解析 Diff，先由规则引擎做确定性风险扫描，
再由 AI 做语义分析和自然建议，最终输出结构化 Markdown Review。
```

## 模块结构

```text
chapter05-ai-code-review
├── git-webhook-adapter       ← Webhook 签名校验、平台适配（后续扩展）
├── diff-analysis-service     ← 规则引擎 + AI Review 服务化（后续扩展）
├── review-comment-publisher  ← 评论发布到 GitHub / Gitee（后续扩展）
├── sample-data               ← 样例 Diff 和 Review 输出
└── api.http
```

当前已实现不依赖外部框架的纯 Java 8 Stub Demo：

```text
src/main/java/com/ynzz/lab/chapter05
├── Chapter05Demo.java
├── diff
│   ├── GitDiffParser.java
│   ├── DiffModel.java
│   └── ChangedLine.java
├── review
│   ├── RuleBasedRiskScanner.java
│   ├── AiReviewService.java          ← AI 分析层接口
│   ├── StubAiReviewService.java      ← Stub 实现（后续替换为 Spring AI）
│   ├── CodeReviewService.java
│   ├── ReviewFinding.java
│   ├── ReviewResult.java
│   └── ReviewCommentFormatter.java
└── webhook
    ├── GitWebhookRequest.java
    └── GitWebhookController.java
```

## 双层防线架构

```text
Git Webhook / Push
        ↓
GitDiffParser
        ↓
┌──────────────────────────┐
│ 第一层：规则扫描            │  ← 空指针 / SQL 注入 / 事务 / 敏感字段
│ RuleBasedRiskScanner      │
└──────────────────────────┘
        ↓
┌──────────────────────────┐
│ 第二层：AI 分析（预留）      │  ← 语义理解 / 上下文补充 / 自然建议
│ AiReviewService           │
└──────────────────────────┘
        ↓
ReviewCommentFormatter
        ↓
Markdown Review
```

## 演示流程

```text
1. Git 平台推送 Webhook
2. Webhook Adapter 接收或拉取 Diff
3. RuleBasedRiskScanner 做确定性风险扫描
4. AiReviewService 做语义分析和建议增强（当前 Stub 透传）
5. ReviewCommentFormatter 输出 Markdown 评论
```

## 本地运行

在当前目录执行：

```powershell
.\compile-and-run.ps1
```

或手动执行：

```powershell
New-Item -ItemType Directory -Force -Path target\classes
javac -encoding UTF-8 -d target\classes (Get-ChildItem -Recurse src\main\java\*.java)
java -cp target\classes com.ynzz.lab.chapter05.Chapter05Demo
```

预期输出：

- 风险等级：`MEDIUM`。
- 空指针风险：新增代码直接读取 `request.getUserId()` / `request.getProductId()` 但未判空。
- 参数校验缺失：提取了业务参数但无 null / isEmpty 检查。
- 库存校验缺失：新增 `TODO check product stock later`。
- Webhook 入口模拟：通过 `GitWebhookController` 触发相同 Review 管道。

## API

```http
POST /webhooks/git/review
```

输入：

```json
{
  "repo": "legacy-order",
  "branch": "feature/order-create",
  "commitId": "abc123",
  "diffText": "..."
}
```

输出：

```json
{
  "riskLevel": "MEDIUM",
  "findings": [
    {
      "type": "null-safety",
      "riskLevel": "MEDIUM",
      "fileName": "OrderController.java",
      "lineNumber": 2,
      "title": "可能的空指针风险",
      "reason": "新增代码直接读取 request 字段，但没有判断 request 是否为空。",
      "suggestion": "在进入业务逻辑前增加请求对象校验。"
    }
  ]
}
```

## 企业边界

- 只输出评论，不自动修改代码。
- 高风险问题必须人工确认。
- 规则扫描和 AI 分析双层检查，互补不替代。
- Review 输出必须结构化：文件、行号、风险等级、类型、原因、建议。

## 规则维度

| 维度 | 检测内容 | Demo 实现 |
|------|---------|----------|
| 空指针风险 | request.getXxx() 无判空 | ✅ |
| 参数校验缺失 | 业务参数无 null/empty 检查 | ✅ |
| 库存/TODO 缺失 | TODO 标记的待补逻辑 | ✅ |
| SQL 注入风险 | 字符串拼接 SQL | 后续 |
| 事务边界 | 写库操作无 @Transactional | 后续 |
| 敏感字段暴露 | 日志打印手机号/身份证 | 后续 |
