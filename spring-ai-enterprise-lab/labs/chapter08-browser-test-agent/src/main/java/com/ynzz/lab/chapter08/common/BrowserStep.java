package com.ynzz.lab.chapter08.common;

public class BrowserStep {
    private final BrowserAction action;
    private final String target;
    private final String value;
    private final boolean screenshotAfter;

    public BrowserStep(BrowserAction action, String target, String value, boolean screenshotAfter) {
        this.action = action;
        this.target = target;
        this.value = value;
        this.screenshotAfter = screenshotAfter;
    }

    public BrowserAction getAction() {
        return action;
    }

    public String getTarget() {
        return target;
    }

    public String getValue() {
        return value;
    }

    public String toJson() {
        if (BrowserAction.TYPE.equals(action)) {
            return "{\"action\": \"TYPE\", \"inputRef\": \"" + escape(target)
                    + "\", \"value\": \"" + escape(value == null ? "" : value) + "\"}";
        }
        if (BrowserAction.CLICK.equals(action)) {
            return "{\"action\": \"CLICK\", \"ref\": \"" + escape(target) + "\"}";
        }
        if (BrowserAction.SCREENSHOT.equals(action)) {
            return "{\"action\": \"SCREENSHOT\", \"element\": \"" + escape(target)
                    + "\", \"path\": \"" + escape(value == null ? "" : value) + "\"}";
        }
        return "{\"action\": \"" + action.name() + "\", \"target\": \"" + escape(target) + "\"}";
    }

    private String escape(String value) {
        return value.replace("\\", "\\\\").replace("\"", "\\\"");
    }
}
