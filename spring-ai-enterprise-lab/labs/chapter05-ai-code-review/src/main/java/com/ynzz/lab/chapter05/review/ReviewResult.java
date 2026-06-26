package com.ynzz.lab.chapter05.review;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ReviewResult {
    private final String riskLevel;
    private final List<ReviewFinding> findings;

    public ReviewResult(String riskLevel, List<ReviewFinding> findings) {
        this.riskLevel = riskLevel;
        this.findings = new ArrayList<>(findings);
    }

    public String getRiskLevel() {
        return riskLevel;
    }

    public List<ReviewFinding> getFindings() {
        return Collections.unmodifiableList(findings);
    }
}
