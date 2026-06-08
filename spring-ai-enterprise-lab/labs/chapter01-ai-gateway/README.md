# Chapter 01 - Java 8 老系统旁路接入 AI Gateway

## 本讲定位

本讲是公开系列 10 讲的总入口。

它不做完整 AI 能力中心项目，而是先用一个 Maven 可运行 Demo 讲清楚：

```text
Java 8 老系统不升级
        ↓
通过稳定客户端旁路调用 AI Gateway
        ↓
AI Gateway 负责脱敏、审计、幂等、限流、熔断和降级
        ↓
老系统只展示 AI 建议，不自动修改业务状态
```

## 本讲目标

证明一件事：

```text
Java 8 老系统不用升级到 Spring Boot 3，也能通过旁路 AI Gateway 接入 AI 能力。
```

本讲重点不是 Spring AI 的真实接入细节，而是企业接入 AI 时最基础的边界：

- 老系统少改。
- 敏感数据先脱敏。
- AI 调用要审计。
- 重复请求要幂等。
- 模型不可用要降级。
- AI 只给建议，不直接改订单状态。

现在本讲已经支持可选旁路 AI Center：

```text
不设置 AI_CENTER_BASE_URL：使用本地 StubModelClient
设置 AI_CENTER_BASE_URL：通过 HTTP 调用 ../ai-center-mvp 的 Spring AI 1.1.7 MVP
```

## 模块结构

```text
chapter01-ai-gateway
├── sample-data
│   └── 订单样例数据
├── src/main/java/com/ynzz/lab/chapter01
│   ├── common
│   │   └── 请求和响应对象
│   ├── legacy
│   │   └── 模拟 Java 8 老订单系统
│   ├── gateway
│   │   └── 旁路 AI Gateway 最小实现
│   └── Chapter01Demo.java
├── pom.xml
├── compile-and-run.ps1
└── api.http
```

核心调用链：

```text
LegacyOrderService
        ↓
AiGatewayClient
        ↓
OrderSummaryService
        ↓
GatewayPipeline
        ↓
Validation / Idempotency / RateLimit / Masking / Audit / CircuitBreaker / ModelCall / Parse / AuditResponse
```

## 本地运行

在当前目录执行：

```powershell
.\compile-and-run.ps1
```

如果要调用旁路 AI Center，先启动：

```powershell
cd ..\..\ai-center-mvp
.\compile-and-run.ps1
```

再回到本章目录执行：

```powershell
$env:AI_CENTER_BASE_URL="http://localhost:18080"
.\compile-and-run.ps1
```

脚本会优先使用：

```text
MAVEN_HOME/bin/mvn.cmd
D:\workspace\apache-maven-3.9.12\bin\mvn.cmd
PATH 中的 mvn
```

等效 Maven 命令：

```powershell
mvn spring-boot:run
```

## 运行场景

运行后可以看到 4 个核心场景。

### 场景 1：老系统旁路调用 AI Gateway

输入包含手机号、身份证和邮箱。

预期观察点：

- 审计日志包含 `maskedFields=[idCard, mobile, email]`。
- 返回结构化摘要、风险等级和建议动作。
- 老系统订单状态调用前后保持不变。

输出中会出现：

```text
legacyOrderStatusBefore=DELAYED, legacyOrderStatusAfter=DELAYED
boundary=AI 只生成处理建议，不自动修改老系统订单状态。
```

### 场景 2：重复请求命中幂等缓存

相同订单重复请求时，直接返回缓存结果。

预期观察点：

- 不再进入模型调用链路。
- 避免重复调用模型造成额外成本和重复处理。

### 场景 3：模型不可用时熔断降级

模拟 `StubModelClient` 不可用。

预期观察点：

- 返回 `fallback=true`。
- 老系统仍拿到固定处理建议。
- 不会因为 AI 不可用影响老系统主流程。

### 场景 4：参数校验失败短路

传入空 `orderId`。

预期观察点：

- 请求不会进入模型调用。
- 返回参数错误提示。

## API

```http
POST /ai/tasks/order-summary
```

输入：

```json
{
  "tenantId": "demo",
  "operatorId": "u1001",
  "orderId": "O202606050001",
  "orderText": "客户反馈订单延迟发货，希望今天给出处理方案。手机号 13800000000"
}
```

输出：

```json
{
  "orderId": "O202606050001",
  "summary": "客户反馈订单延迟发货，需要尽快给出处理方案。",
  "riskLevel": "MEDIUM",
  "suggestedActions": [
    "查询物流状态",
    "联系仓库确认发货时间",
    "向客户同步预计处理时间"
  ],
  "fallback": false,
  "maskedFields": ["mobile"]
}
```

## 企业边界

- `ValidationFilter`：非法请求短路，不进入模型。
- `IdempotencyFilter`：相同订单重复请求返回缓存，避免重复调用。
- `RateLimitFilter`：限制老系统对 AI Gateway 的调用频率。
- `MaskingFilter`：手机号、身份证、邮箱脱敏后再进入模型调用。
- `AuditRequestFilter` / `AuditResponseFilter`：记录 traceId、租户、操作人、订单和结果。
- `CircuitBreakerFilter`：模型不可用时直接降级。
- `ModelCallFilter`：模型调用与少量重试。
- `ParseResponseFilter`：把模型结果整理成结构化响应。

## 文章反写角度

文章不要写成 “Spring AI Hello World”。

更适合的叙事是：

```text
老系统不敢升级，业务又要接 AI。
Java 程序员第一步不是接模型，而是先设计一个旁路 AI Gateway。
```

这一讲是公开系列总纲：先把“老系统少改 + AI 能力旁路 + 企业边界先行”这条主线跑通，再逐讲扩展不同业务场景。
