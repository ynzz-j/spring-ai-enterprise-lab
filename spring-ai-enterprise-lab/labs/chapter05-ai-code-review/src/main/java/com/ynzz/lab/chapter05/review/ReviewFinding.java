package com.ynzz.lab.chapter05.review;

public class ReviewFinding {
    private final String type;
    private final String riskLevel;
    private final String fileName;
    private final int lineNumber;
    private final String title;
    private final String reason;
    private final String suggestion;

    public ReviewFinding(String type, String riskLevel, String fileName, int lineNumber,
                         String title, String reason, String suggestion) {
        this.type = type;
        this.riskLevel = riskLevel;
        this.fileName = fileName;
        this.lineNumber = lineNumber;
        this.title = title;
        this.reason = reason;
        this.suggestion = suggestion;
    }

    public String getType() {
        return type;
    }

    public String getRiskLevel() {
        return riskLevel;
    }

    public String getFileName() {
        return fileName;
    }

    public int getLineNumber() {
        return lineNumber;
    }

    public String getTitle() {
        return title;
    }

    public String getReason() {
        return reason;
    }

    public String getSuggestion() {
        return suggestion;
    }
}

