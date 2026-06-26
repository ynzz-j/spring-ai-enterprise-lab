# review-comment-publisher package

这里后续放 Review 评论发布代码。

建议类：

- `ReviewCommentFormatter`
- `MarkdownReviewRenderer`
- `GithubReviewPublisher`
- `GiteeReviewPublisher`
- `ConsoleReviewPublisher`

设计约束：

- 默认先输出到控制台或 Markdown 文件。
- 接入真实 Git 平台前要保留人工确认。
- 发布内容要包含风险等级、文件位置、原因和建议。
- 不能自动提交修复代码。

