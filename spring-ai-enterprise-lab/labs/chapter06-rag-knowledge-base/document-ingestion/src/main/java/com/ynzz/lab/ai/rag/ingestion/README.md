# document-ingestion package

这里后续放文档入库代码。

建议类：

- `DocumentIngestionController`
- `DocumentCleaner`
- `SensitiveTextMasker`
- `ChunkingStrategy`
- `EmbeddingIndexService`
- `DocumentAclExtractor`

设计约束：

- 先脱敏再索引。
- 每个 Chunk 带来源、租户、角色权限。
- 文档切分策略要可替换。

