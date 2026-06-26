# Chapter 04 - Java 8 老项目代码结构事实

## 本讲目标

第 4 讲先不让 AI 直接改代码，也不把完整源码一次性丢给模型。

本讲先跑通“事实层”：

- 扫描 Java 8 老项目源码。
- 识别 `controller / service / mapper` 分层。
- 找到入口方法。
- 生成最小调用链。
- 输出一条新人能看懂的阅读路线。

核心口径：

```text
结构化事实在前，AI 解释在后。
```

## 模块结构

```text
chapter04-code-reading
├── legacy-codebase-sample
│   └── Java 8 老项目样例
├── code-analysis-service
│   └── 代码扫描、分层识别、调用链和阅读路线生成说明
├── src/main/java/com/ynzz/lab/chapter04
│   ├── Chapter04Demo.java
│   ├── analysis
│   └── common
├── api.http
├── compile-and-run.ps1
└── pom.xml
```

当前 Demo 链路：

```text
legacy-codebase-sample
        ↓
JavaProjectScanner
        ↓
LayerDetector
        ↓
CallChainAnalyzer
        ↓
ReadingPathGenerator
```

## 样例老项目

样例项目包含：

- `OrderController`：请求入口，接收 `clientVersion`。
- `OrderService`：业务校验、历史兼容分支、异常兜底。
- `OrderMapper`：打印可观察的 INSERT 动作，并保留历史字段注释。

这让第 4 讲不只是干净的正向流程，而是能体现老 Java 8 项目常见的阅读难点。

## 本地运行

在当前目录执行：

```powershell
.\compile-and-run.ps1
```

预期能看到：

- 识别 `controller / service / mapper` 分层。
- 生成 `OrderController#createOrder -> OrderService#createOrder -> OrderMapper#insert` 调用链。
- 生成阅读路线。

## API 草案

```http
POST /ai/code/reading-path
```

输入：

```json
{
  "projectName": "legacy-order",
  "projectPath": "./legacy-codebase-sample",
  "entrypoint": "OrderController#createOrder"
}
```

输出：

```json
{
  "projectName": "legacy-order",
  "entrypoints": ["OrderController#createOrder"],
  "layers": ["controller", "service", "mapper"],
  "callChain": [
    "OrderController#createOrder",
    "OrderService#createOrder",
    "OrderMapper#insert"
  ],
  "readingPath": [
    "先读 OrderController#createOrder，理解请求入口和参数。",
    "再读 OrderService#createOrder，理解核心业务规则和异常分支。",
    "最后读 OrderMapper#insert，理解数据落库和持久化边界。"
  ]
}
```

## 企业边界

- 默认只分析本地样例代码。
- 不把完整源码一次性发给模型。
- 调用链、类名、分层先由规则生成。
- AI 后续只基于结构化事实做解释、摘要和问答。
- 人仍负责确认兼容分支、异常兜底和历史字段是否能调整。

## 文章反写角度

第 4 讲是前三讲之后的过渡章：

```text
前三讲：AI 如何安全接入老系统。
第 4 讲：AI 和团队如何先读懂老系统。
```

文章重点不是“AI 写代码”，而是“先给 AI 一张结构化的代码地图”。
