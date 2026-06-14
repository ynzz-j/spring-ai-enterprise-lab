# Chapter 03 - SQL Agent 安全查库

## 本讲目标

把“自然语言查询企业数据库”做成企业能接受的版本：

```text
AI 可以生成 SQL，但 SQL 必须经过安全引擎校验后才能执行。
```

## 模块结构

```text
chapter03-sql-agent
├── legacy-report-db
│   └── 模拟 Java 8 老系统沉淀出来的报表库
├── sql-agent-service
│   └── 负责自然语言理解、SQL 生成、结果摘要
├── sql-safety-engine
│   └── 负责 SQL 白名单、只读校验、LIMIT、超时
├── sample-data
│   └── 查询问题和预期输出
└── api.http
```

当前已补充一个 Java 8 基础 Demo：

```text
src/main/java/com/ynzz/lab/chapter03
├── Chapter03Demo.java
├── agent
├── common
└── safety
```

它用于先跑通 SQL 安全链路：

```text
question -> SqlGenerateService -> SqlSafetyEngine -> ReadOnlySqlExecutor -> SqlResultSummarizer
```

默认使用 `StubSqlGenerateService`。如果设置 `AI_CENTER_BASE_URL=http://localhost:18080`，会调用 `ai-center-mvp` 的 `/api/mvp/sql-candidate` 生成候选 SQL。

无论候选 SQL 来自 Stub 还是真实模型，都必须经过 `SqlSafetyEngine`。

## 演示流程

```text
1. 用户输入自然语言问题
2. SQL Agent 读取 Schema 快照
3. 模型生成候选 SQL
4. SQL Safety Engine 做安全校验
5. 只读执行器查询数据
6. AI 总结查询结果
```

## 本地运行

在当前目录执行：

```powershell
.\compile-and-run.ps1
```

预期能看到 4 类结果：

- 合法销售额查询：`blocked=false`，返回摘要和行数据。
- 合法退款数量查询：`blocked=false`，缺少 `LIMIT` 时自动补齐。
- 删除意图：`blocked=true`，`blockReason=WRITE_OPERATION_NOT_ALLOWED`。
- 手机号查询：`blocked=true`，`blockReason=SENSITIVE_FIELD_NOT_ALLOWED`。

## API

```http
POST /ai/sql/query
```

输入：

```json
{
  "tenantId": "demo",
  "operatorId": "u1001",
  "question": "统计本月销售额最高的 10 个商品"
}
```

输出：

```json
{
  "question": "统计本月销售额最高的 10 个商品",
  "sql": "SELECT product_name, SUM(amount) AS total_amount FROM order_report WHERE order_month = '2026-06' GROUP BY product_name ORDER BY total_amount DESC LIMIT 10",
  "blocked": false,
  "summary": "本月销售额最高的商品是 AI 开发训练营，销售额 128000 元。",
  "rows": []
}
```

## 企业边界

- 只允许 `SELECT`。
- 禁止执行 `UPDATE`、`DELETE`、`INSERT`、`DROP`、`ALTER`。
- 表名和字段名必须在白名单里。
- 敏感字段默认不可查。
- 所有 SQL 强制追加或校验 `LIMIT`。
- 查询超时后终止。

## 文章反写角度

文章重点不是“AI 会写 SQL”，而是：

> 企业真正担心的是 AI 写出危险 SQL，怎么让 Text2SQL 变成可控能力？
