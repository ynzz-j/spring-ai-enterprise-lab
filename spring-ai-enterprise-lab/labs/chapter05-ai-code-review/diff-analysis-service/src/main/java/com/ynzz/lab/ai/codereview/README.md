# diff-analysis-service package

这里后续放 Diff 分析和 AI Review 代码。

建议类：

- `GitDiffParser`
- `ChangedFile`
- `ChangedLine`
- `ReviewContextBuilder`
- `RuleBasedRiskScanner`
- `AiReviewService`
- `ReviewFinding`
- `TrainingSuggestionBuilder`

设计约束：

- 先解析 Diff，再构造 Review 上下文。
- 规则扫描负责确定性问题。
- AI Review 负责解释风险和补充建议。
- 输出必须结构化，方便发布评论和生成 CodeStep 训练建议。

