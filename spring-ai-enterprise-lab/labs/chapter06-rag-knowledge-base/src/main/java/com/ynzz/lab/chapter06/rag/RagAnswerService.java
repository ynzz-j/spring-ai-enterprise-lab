package com.ynzz.lab.chapter06.rag;

import com.ynzz.lab.chapter06.common.KnowledgeAnswer;
import com.ynzz.lab.chapter06.common.KnowledgeAskRequest;
import com.ynzz.lab.chapter06.ingestion.DocumentChunk;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RagAnswerService {
    private final List<DocumentChunk> chunks;
    private final KeywordRetriever retriever;

    public RagAnswerService(List<DocumentChunk> chunks, KeywordRetriever retriever) {
        this.chunks = new ArrayList<DocumentChunk>(chunks);
        this.retriever = retriever;
    }

    public KnowledgeAnswer ask(KnowledgeAskRequest request) {
        List<DocumentChunk> evidence = retriever.retrieve(request.getQuestion(), request.getRole(), chunks);
        List<String> filters = Arrays.asList(
                "tenant=" + request.getTenantId(),
                "role=" + request.getRole(),
                "topK=3");

        if (evidence.isEmpty()) {
            return new KnowledgeAnswer(
                    "不确定：知识库中没有找到当前角色可引用的证据。",
                    true,
                    new ArrayList<Citation>(),
                    filters);
        }

        DocumentChunk best = evidence.get(0);
        List<Citation> citations = new ArrayList<Citation>();
        for (DocumentChunk chunk : evidence) {
            citations.add(new Citation(chunk.getSource(), chunk.getChunkId()));
        }

        return new KnowledgeAnswer(
                "根据知识库，" + cleanMarkdown(best.getContent()) + " 回答已基于检索证据生成。",
                false,
                citations,
                filters);
    }

    private String cleanMarkdown(String content) {
        return content.replace("`", "");
    }
}

