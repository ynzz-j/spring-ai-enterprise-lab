package com.ynzz.lab.chapter08.common;

public class BrowserTestRequest {
    private final String environment;
    private final String targetUrl;
    private final String task;

    public BrowserTestRequest(String environment, String targetUrl, String task) {
        this.environment = environment;
        this.targetUrl = targetUrl;
        this.task = task;
    }

    public String getEnvironment() {
        return environment;
    }

    public String getTargetUrl() {
        return targetUrl;
    }

    public String getTask() {
        return task;
    }
}

