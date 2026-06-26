# Chapter 06 - 企业知识库 RAG

## 本讲目标

把接口文档、数据库字典、运维手册变成可引用、可限权的企业 AI 助手。

## 模块结构

```text
chapter06-rag-knowledge-base
├── document-ingestion
│   └── 文档清洗、脱敏、切分、索引
├── rag-service
│   └── 检索、回答、权限过滤
├── citation-viewer
│   └── 引用来源展示
├── sample-data
│   └── 企业文档样例
└── api.http
```

## 演示流程

```text
1. 导入接口文档和数据库字典
2. 文档脱敏后切分
3. 建立向量索引
4. 用户提问
5. 按角色过滤可检索文档
6. AI 基于检索结果回答并给出引用
```

## 本地运行

当前目录提供 Java 8 纯 Stub 版本，会读取 `sample-data/docs` 下的 Markdown 文档并模拟 RAG 检索。

```powershell
.\compile-and-run.ps1
```

可观察结果：

- `DELAYED` 订单状态问题会命中 `order-status.md`。
- 回答会带 citation，包含来源文件和 chunkId。
- developer 角色无法读取 `pricing-policy.md` 的财务资料。
- 没有当前角色可引用证据时，回答为“不确定”。

## API

```http
POST /ai/kb/ask
```

输入：

```json
{
  "tenantId": "demo",
  "operatorId": "u1001",
  "role": "developer",
  "question": "订单状态 DELAYED 代表什么？"
}
```

## 企业边界

- 文档入库前脱敏。
- 回答必须带引用。
- 没有证据时必须回答“不确定”。
- 按租户、部门、角色过滤文档。
- 不把全部知识库上下文直接塞给模型。

## 文章反写角度

企业 RAG 的难点不是向量库，而是权限、脱敏、引用和持续维护。
