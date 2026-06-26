package com.ynzz.lab.chapter05.webhook;

/**
 * Git Webhook 请求体。
 */
public class GitWebhookRequest {
    private String repo;
    private String branch;
    private String commitId;
    private String diffText;

    public GitWebhookRequest() {
    }

    public GitWebhookRequest(String repo, String branch, String commitId, String diffText) {
        this.repo = repo;
        this.branch = branch;
        this.commitId = commitId;
        this.diffText = diffText;
    }

    public String getRepo() {
        return repo;
    }

    public void setRepo(String repo) {
        this.repo = repo;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getCommitId() {
        return commitId;
    }

    public void setCommitId(String commitId) {
        this.commitId = commitId;
    }

    public String getDiffText() {
        return diffText;
    }

    public void setDiffText(String diffText) {
        this.diffText = diffText;
    }
}
