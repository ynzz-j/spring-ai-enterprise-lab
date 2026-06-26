# code-analysis-service package

这里后续放代码分析服务。

建议类：

- `JavaProjectScanner`
- `JavaSourceFile`
- `LayerDetector`
- `CallChainAnalyzer`
- `CodeChunker`
- `ArchitectureSummaryService`
- `ReadingPathGenerator`
- `ReadingPathRequest`
- `ReadingPathResponse`

设计约束：

- 先用规则识别结构，再让 AI 做总结。
- 不把整个项目一次性塞给模型。
- 调用链优先用静态分析或约定规则生成，AI 负责解释。
- 输出先服务阅读路线，后续再扩展解释、摘要和问答。
