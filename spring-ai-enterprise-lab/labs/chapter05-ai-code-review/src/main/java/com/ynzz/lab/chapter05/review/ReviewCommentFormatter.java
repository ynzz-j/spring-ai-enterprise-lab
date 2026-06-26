package com.ynzz.lab.chapter05.review;

public class ReviewCommentFormatter {
    public String toMarkdown(ReviewResult result) {
        StringBuilder builder = new StringBuilder();
        builder.append("### AI Review\n\n");
        builder.append("风险等级：").append(result.getRiskLevel()).append("\n\n");

        int index = 1;
        for (ReviewFinding finding : result.getFindings()) {
            builder.append("#### ").append(index).append(". ").append(finding.getTitle()).append("\n\n");
            builder.append("- 文件：`").append(finding.getFileName()).append("`\n");
            builder.append("- 位置：第 ").append(finding.getLineNumber()).append(" 行\n");
            builder.append("- 原因：").append(finding.getReason()).append("\n");
            builder.append("- 建议：").append(finding.getSuggestion()).append("\n\n");
            index++;
        }
        return builder.toString();
    }
}
