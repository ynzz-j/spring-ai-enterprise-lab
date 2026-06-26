# rag-service package

这里后续放 RAG 问答服务。

建议类：

- `KnowledgeAskController`
- `KnowledgeSearchService`
- `RoleAwareRetriever`
- `RagPromptBuilder`
- `RagAnswerService`
- `NoEvidenceAnswerPolicy`

设计约束：

- 检索先过权限过滤。
- 回答必须引用来源。
- 没有证据时不能编造。

