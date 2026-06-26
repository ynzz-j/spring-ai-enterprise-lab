# Chapter 07 - AI 工单助手

## 本讲目标

让老系统把工单事件推送给 AI 能力中心，由 Agent 完成分类、摘要、优先级识别和处理人推荐。

## 模块结构

```text
chapter07-ticket-agent
├── legacy-ticket-system
│   └── 模拟 Java 8 工单系统
├── ticket-agent-service
│   └── 工单摘要、分类、推荐处理人
├── assignment-policy
│   └── 技能、排班、SLA 策略
├── sample-data
│   └── 工单样例和推荐结果
└── api.http
```

## 演示流程

```text
1. 老工单系统推送新工单事件
2. Ticket Agent 摘要问题
3. Ticket Agent 判断分类、优先级和 SLA 风险
4. 调用 AssigneeRecommendTool 查询人员技能和排班
5. 输出推荐处理人
6. 人工确认后才派单
```

## 本地运行

当前目录提供 Java 8 纯 Stub 版本，先验证工单 Agent 的企业边界。

```powershell
.\compile-and-run.ps1
```

可观察结果：

- 支付异常工单会被分类为 `PAYMENT`，优先级为 `HIGH`。
- 工单手机号会被脱敏。
- 推荐处理人会给出理由。
- AI 推荐后状态是 `WAITING_FOR_HUMAN_CONFIRMATION`。
- 只有人工确认接口才会进入 `ASSIGNED_AFTER_HUMAN_CONFIRMATION`。

## API

```http
POST /ai/tickets/analyze
```

## 企业边界

- AI 只推荐，不直接派单。
- 高优先级工单必须人工确认。
- 工单内容需要脱敏。
- 推荐处理人必须给出理由。
- 推荐结果要写入审计日志。

## CodeStep 连接点

工单中的问题类型可以反向生成学习计划：

```text
支付异常工单
        ↓
支付链路阅读训练
        ↓
CodeStep 专项任务
```

## 文章反写角度

Agent 不一定要上来就做“自动开发”，先落到工单分类、摘要、推荐处理人，更接近企业日常流程。
