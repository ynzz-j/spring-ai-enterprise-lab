package com.ynzz.lab.chapter05.review;

import com.ynzz.lab.chapter05.diff.DiffModel;

import java.util.List;

/**
 * AI 分析层接口。
 * <p>
 * 规则扫描负责确定性检测（空指针、SQL注入、事务等），
 * AI 分析负责语义理解：解释风险原因、补充上下文、生成自然建议。
 * <p>
 * 当前由 StubAiReviewService 透传，后续接入 Spring AI 时替换实现。
 */
public interface AiReviewService {

    /**
     * 基于规则扫描结果和 Diff 上下文，生成 AI 补充分析。
     *
     * @param findings 规则扫描产出的风险列表
     * @param diff     当前 Diff 上下文
     * @return 增强后的风险列表（可补充语义解释和自然建议）
     */
    List<ReviewFinding> enrich(List<ReviewFinding> findings, DiffModel diff);
}
