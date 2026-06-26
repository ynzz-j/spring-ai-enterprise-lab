package com.ynzz.lab.chapter06.rag;

import com.ynzz.lab.chapter06.ingestion.DocumentChunk;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class KeywordRetriever {
    private final RoleDocumentPolicy roleDocumentPolicy;

    public KeywordRetriever(RoleDocumentPolicy roleDocumentPolicy) {
        this.roleDocumentPolicy = roleDocumentPolicy;
    }

    public List<DocumentChunk> retrieve(String question, String role, List<DocumentChunk> chunks) {
        List<ScoredChunk> scoredChunks = new ArrayList<ScoredChunk>();
        for (DocumentChunk chunk : chunks) {
            if (!roleDocumentPolicy.canRead(role, chunk)) {
                continue;
            }
            int score = score(question, chunk.getContent());
            if (score > 0) {
                scoredChunks.add(new ScoredChunk(chunk, score));
            }
        }

        Collections.sort(scoredChunks, new Comparator<ScoredChunk>() {
            public int compare(ScoredChunk left, ScoredChunk right) {
                return right.score - left.score;
            }
        });

        List<DocumentChunk> result = new ArrayList<DocumentChunk>();
        for (int i = 0; i < scoredChunks.size() && i < 3; i++) {
            result.add(scoredChunks.get(i).chunk);
        }
        return result;
    }

    private int score(String question, String content) {
        int score = 0;
        String upperQuestion = question.toUpperCase();
        String upperContent = content.toUpperCase();
        if (upperQuestion.contains("DELAYED") && upperContent.contains("DELAYED")) {
            score += 10;
        }
        if (question.contains("订单状态") && content.contains("订单")) {
            score += 2;
        }
        if (question.contains("定价") && content.contains("定价")) {
            score += 10;
        }
        if (question.contains("策略") && content.contains("策略")) {
            score += 3;
        }
        if (question.contains("退款") && content.contains("REFUND")) {
            score += 5;
        }
        return score;
    }

    private static class ScoredChunk {
        private final DocumentChunk chunk;
        private final int score;

        private ScoredChunk(DocumentChunk chunk, int score) {
            this.chunk = chunk;
            this.score = score;
        }
    }
}

