package com.ynzz.lab.chapter05;

import com.ynzz.lab.chapter05.diff.DiffModel;
import com.ynzz.lab.chapter05.diff.GitDiffParser;
import com.ynzz.lab.chapter05.review.*;
import com.ynzz.lab.chapter05.webhook.GitWebhookController;
import com.ynzz.lab.chapter05.webhook.GitWebhookRequest;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;

public class Chapter05Demo {
    public static void main(String[] args) throws IOException {
        // 方式一：直接调用 Review 管道
        String diffText = read("sample-data/sample.diff");
        DiffModel diff = new GitDiffParser().parse(diffText);
        ReviewResult result = new CodeReviewService(
                new RuleBasedRiskScanner(),
                new StubAiReviewService()).review(diff);
        String markdown = new ReviewCommentFormatter().toMarkdown(result);

        System.out.println("=== 直接调用 Review 管道 ===");
        System.out.println(markdown);

        // 方式二：通过 Webhook 入口（模拟 Git 平台推送）
        System.out.println("=== Webhook 入口模拟 ===");
        GitWebhookController controller = new GitWebhookController(
                new CodeReviewService(new RuleBasedRiskScanner(), new StubAiReviewService()));
        GitWebhookRequest webhookRequest = new GitWebhookRequest(
                "legacy-order", "feature/order-create", "abc123", diffText);
        System.out.println(controller.handleReviewWebhook(webhookRequest));
    }

    private static String read(String path) throws IOException {
        byte[] bytes = Files.readAllBytes(new File(path).toPath());
        return new String(bytes, Charset.forName("UTF-8"));
    }
}
