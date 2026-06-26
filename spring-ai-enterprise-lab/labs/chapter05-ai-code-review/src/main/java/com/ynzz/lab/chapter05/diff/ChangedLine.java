package com.ynzz.lab.chapter05.diff;

public class ChangedLine {
    private final int lineNumber;
    private final String content;

    public ChangedLine(int lineNumber, String content) {
        this.lineNumber = lineNumber;
        this.content = content;
    }

    public int getLineNumber() {
        return lineNumber;
    }

    public String getContent() {
        return content;
    }
}

