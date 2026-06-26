# git-webhook-adapter package

这里后续放 Git Webhook 接入代码。

建议类：

- `GitWebhookController`
- `GitWebhookRequest`
- `GitProvider`
- `DiffFetchService`
- `WebhookSignatureVerifier`

设计约束：

- 支持 GitHub / Gitee 的差异化适配。
- Webhook 签名必须校验。
- 只接收 Pull Request / Merge Request / Push 相关事件。
- Webhook 只负责接入，不承载 Review 逻辑。

