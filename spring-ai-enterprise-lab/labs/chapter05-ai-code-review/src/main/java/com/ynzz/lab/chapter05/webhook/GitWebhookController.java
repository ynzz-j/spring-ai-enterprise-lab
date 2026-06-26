package com.ynzz.lab.chapter05.webhook;

import com.ynzz.lab.chapter05.diff.DiffModel;
import com.ynzz.lab.chapter05.diff.GitDiffParser;
import com.ynzz.lab.chapter05.review.*;

/**
 * Git Webhook 控制器 Stub。
 * <p>
 * 当前模拟 Webhook 入口：接收 diffText，触发 Review 管道。
 * 后续接入真实 Webhook 时需增加签名校验和平台适配。
 */
public class GitWebhookController {

    private final CodeReviewService reviewService;

    public GitWebhookController(CodeReviewService reviewService) {
        this.reviewService = reviewService;
    }

    /**
     * 接收 Webhook 请求，触发 Review 管道。
     */
    public String handleReviewWebhook(GitWebhookRequest request) {
        DiffModel diff = new GitDiffParser().parse(request.getDiffText());
        ReviewResult result = reviewService.review(diff);
        return new ReviewCommentFormatter().toMarkdown(result);
    }
}
