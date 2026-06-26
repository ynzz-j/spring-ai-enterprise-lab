package com.ynzz.lab.chapter06.common;

import com.ynzz.lab.chapter06.rag.Citation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class KnowledgeAnswer {
    private final String answer;
    private final boolean uncertain;
    private final List<Citation> citations;
    private final List<String> filters;

    public KnowledgeAnswer(String answer, boolean uncertain, List<Citation> citations, List<String> filters) {
        this.answer = answer;
        this.uncertain = uncertain;
        this.citations = new ArrayList<Citation>(citations);
        this.filters = new ArrayList<String>(filters);
    }

    public String toJson() {
        StringBuilder builder = new StringBuilder();
        builder.append("{\n");
        builder.append("  \"answer\": \"").append(escape(answer)).append("\",\n");
        builder.append("  \"uncertain\": ").append(uncertain).append(",\n");
        builder.append("  \"filters\": ").append(stringArray(filters)).append(",\n");
        builder.append("  \"citations\": [");
        for (int i = 0; i < citations.size(); i++) {
            if (i > 0) {
                builder.append(", ");
            }
            Citation citation = citations.get(i);
            builder.append("{\"source\": \"").append(escape(citation.getSource())).append("\", ");
            builder.append("\"chunkId\": \"").append(escape(citation.getChunkId())).append("\"}");
        }
        builder.append("]\n");
        builder.append("}");
        return builder.toString();
    }

    public List<Citation> getCitations() {
        return Collections.unmodifiableList(citations);
    }

    private String stringArray(List<String> values) {
        StringBuilder builder = new StringBuilder("[");
        for (int i = 0; i < values.size(); i++) {
            if (i > 0) {
                builder.append(", ");
            }
            builder.append("\"").append(escape(values.get(i))).append("\"");
        }
        builder.append("]");
        return builder.toString();
    }

    private String escape(String value) {
        return value.replace("\\", "\\\\").replace("\"", "\\\"");
    }
}

