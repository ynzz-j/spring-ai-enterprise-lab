# spring-ai-enterprise-lab

这是《Java 8 老系统如何接入 AI：Spring AI 企业级实战 10 讲》的配套代码。

当前公开 Lab 保持两个层次：

```text
labs/           Java 8 + Spring Boot 2.7，每讲独立可运行
ai-center-mvp/  Java 17+ + Spring Boot 3.5 + Spring AI 1.1.7，旁路 AI 能力中心 MVP
```

## 课程代码目标

模拟一个企业从 Java 8 老系统接入 AI 的过程：

```text
Java 8 Legacy System
        ↓
AI Capability Center
        ↓
Spring AI / MCP / RAG / Agent / Workflow
```

Spring AI 不直接进入 Java 8 老系统。老系统通过 HTTP 调用旁路 AI 能力中心。

## 章节模块

公开基础 Lab 位于 `labs/` 目录：

- `chapter01-ai-gateway`
- `chapter02-mcp-tool-center`
- `chapter03-sql-agent`
- `chapter04-code-reading`
- `chapter05-ai-code-review`
- `chapter06-rag-knowledge-base`
- `chapter07-ticket-agent`
- `chapter08-browser-test-agent`
- `chapter09-ai-workflow`
- `chapter10-multi-agent-dev-team`

Spring AI 最小 MVP 位于：

```text
ai-center-mvp
```

第 1-3 章设置 `AI_CENTER_BASE_URL=http://localhost:18080` 后会调用该服务；不设置时仍走本地 Stub。

## 当前确认点

请优先确认：

1. 是否接受“Java 8 老系统 + Java 17 AI 能力中心”的双运行时架构。
2. 是否接受每讲一个独立实验模块。
3. 是否把第 4 讲代码阅读和第 5 讲 Code Review 作为 CodeStep 的主要导流点。
4. 第 1-3 章先接入 Spring AI MVP，后续章节再按同样边界逐步增强。
