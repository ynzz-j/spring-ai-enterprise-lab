# Architecture

## 双运行时架构

```text
legacy-java8-system
        ↓
enterprise-adapters
        ↓
ai-capability-center
        ↓
model providers / tools / vector stores
```

## 设计原则

1. Java 8 老系统不直接引入 Spring AI。
2. AI 能力中心独立部署，使用 Java 17+ 和 Spring Boot 3.x 生态。
3. 老系统通过 HTTP、MQ、Webhook 或只读数据源接入 AI 能力。
4. 所有 AI 输出默认是建议，不能默认自动写回生产系统。
5. SQL、派单、代码修改、浏览器操作等高风险动作必须加安全策略。

## 核心包设计

```text
lab-common
├── audit          审计日志
├── auth           权限上下文
├── masking        脱敏
├── rate-limit     限流
├── approval       人工确认
└── task-state     任务状态

legacy-java8-system
├── order          订单样例
├── ticket         工单样例
├── user           用户样例
├── report         报表数据
└── codebase       老项目样例

ai-capability-center
├── gateway        AI 统一入口
├── tools          工具注册
├── agents         Agent 运行时
├── rag            知识库
├── sql            SQL Agent
├── code           代码分析
└── workflow       工作流
```

## 第一批建议实现

先实现这四讲，可以最快形成内容闭环：

1. `chapter01-ai-gateway`：证明老系统可旁路接 AI。
2. `chapter03-sql-agent`：企业读者最容易理解。
3. `chapter04-code-reading`：绑定 CodeStep。
4. `chapter05-ai-code-review`：传播性强，也能绑定 CodeStep。

