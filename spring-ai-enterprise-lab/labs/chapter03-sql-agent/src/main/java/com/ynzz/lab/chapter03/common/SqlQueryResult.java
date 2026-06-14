package com.ynzz.lab.chapter03.common;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SqlQueryResult {
    private final String question;
    private final String sql;
    private final boolean blocked;
    private final String blockReason;
    private final String summary;
    private final List<String> rows;

    public SqlQueryResult(String question, String sql, boolean blocked,
                          String blockReason, String summary, List<String> rows) {
        this.question = question;
        this.sql = sql;
        this.blocked = blocked;
        this.blockReason = blockReason;
        this.summary = summary;
        this.rows = new ArrayList<String>(rows);
    }

    public String getQuestion() {
        return question;
    }

    public String getSql() {
        return sql;
    }

    public boolean isBlocked() {
        return blocked;
    }

    public String getBlockReason() {
        return blockReason;
    }

    public String getSummary() {
        return summary;
    }

    public List<String> getRows() {
        return Collections.unmodifiableList(rows);
    }

    public String toJson() {
        StringBuilder builder = new StringBuilder();
        builder.append("{\n");
        builder.append("  \"question\": \"").append(question).append("\",\n");
        builder.append("  \"sql\": \"").append(sql.replace("\"", "\\\"")).append("\",\n");
        builder.append("  \"blocked\": ").append(blocked).append(",\n");
        builder.append("  \"blockReason\": \"").append(blockReason).append("\",\n");
        builder.append("  \"summary\": \"").append(summary).append("\",\n");
        builder.append("  \"rows\": ").append(toJsonArray(rows)).append("\n");
        builder.append("}");
        return builder.toString();
    }

    private String toJsonArray(List<String> values) {
        StringBuilder builder = new StringBuilder("[");
        for (int i = 0; i < values.size(); i++) {
            if (i > 0) {
                builder.append(", ");
            }
            builder.append("\"").append(values.get(i).replace("\"", "\\\"")).append("\"");
        }
        builder.append("]");
        return builder.toString();
    }
}

