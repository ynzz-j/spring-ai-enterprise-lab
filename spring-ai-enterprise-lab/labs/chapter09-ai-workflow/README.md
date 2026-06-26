# Chapter 09 - AI 工作流编排

## 本讲目标

把一次性 AI 对话升级成可恢复、可审计、可人工确认的企业工作流。

## 模块结构

```text
chapter09-ai-workflow
├── workflow-runtime-demo
│   └── 工作流定义、状态、运行器
├── requirement-to-testcase
│   └── 需求到接口设计、测试用例、发布检查
├── approval-console
│   └── 人工确认节点
├── sample-data
│   └── 工作流定义和运行快照
└── api.http
```

## 演示流程

```text
1. 用户提交需求文档
2. AI 提取需求点
3. AI 生成接口草案
4. 人工确认接口草案
5. AI 生成测试用例
6. AI 生成发布检查清单
7. 输出完整工作流报告
```

## 本地运行

当前目录提供 Java 8 纯 Stub 版本，演示一个可暂停、可审批、可恢复的需求到测试用例工作流。

```powershell
.\compile-and-run.ps1
```

可观察结果：

- 启动后会生成 `requirement-extract` 和 `api-design` 两个节点快照。
- `api-design` 节点会停在 `WAITING_APPROVAL`。
- 人工审批后继续生成 `testcase-generate` 和 `release-checklist`。
- 工作流最终状态为 `COMPLETED`。
- 输出包含不确定项，提醒业务继续确认。

## API

```http
POST /ai/workflows/requirement-to-testcase
POST /ai/workflows/{workflowId}/approvals
```

## 企业边界

- 每个节点有输入输出快照。
- 每个节点可重试。
- 关键节点必须人工确认。
- 失败后可以从上一个成功节点恢复。
- 输出报告必须列出不确定项。

## 文章反写角度

企业 AI 应用不是一次聊天，而是一条稳定流程：可恢复、可审计、可人工介入。
