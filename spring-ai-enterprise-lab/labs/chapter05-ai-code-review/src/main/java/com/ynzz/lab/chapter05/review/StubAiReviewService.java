package com.ynzz.lab.chapter05.review;

import com.ynzz.lab.chapter05.diff.DiffModel;

import java.util.ArrayList;
import java.util.List;

/**
 * AiReviewService 的 Stub 实现，透传规则扫描结果。
 * <p>
 * 后续接入 Spring AI 后，替换为真实实现：根据 Diff 上下文补充语义分析、
 * 解释风险原因、生成更自然的建议措辞。
 */
public class StubAiReviewService implements AiReviewService {

    @Override
    public List<ReviewFinding> enrich(List<ReviewFinding> findings, DiffModel diff) {
        // Stub: 透传，不做修改
        // 后续接入 Spring AI 时，在此处调用模型：
        // 1. 将 findings + diff 上下文构建为 Prompt
        // 2. 让模型解释每个风险的业务含义
        // 3. 返回增强后的 findings
        return new ArrayList<>(findings);
    }
}
