package com.ynzz.lab.chapter05.review;

import com.ynzz.lab.chapter05.diff.DiffModel;

import java.util.List;

public class CodeReviewService {
    private final RuleBasedRiskScanner scanner;
    private final AiReviewService aiReviewService;

    public CodeReviewService(RuleBasedRiskScanner scanner, AiReviewService aiReviewService) {
        this.scanner = scanner;
        this.aiReviewService = aiReviewService;
    }

    public ReviewResult review(DiffModel diff) {
        // 第一层：规则扫描
        List<ReviewFinding> findings = scanner.scan(diff);

        // 第二层：AI 分析
        findings = aiReviewService.enrich(findings, diff);

        return new ReviewResult(highestRisk(findings), findings);
    }

    private String highestRisk(List<ReviewFinding> findings) {
        for (ReviewFinding finding : findings) {
            if ("HIGH".equals(finding.getRiskLevel())) {
                return "HIGH";
            }
        }
        if (!findings.isEmpty()) {
            return "MEDIUM";
        }
        return "LOW";
    }
}
