package com.ynzz.lab.chapter03.safety;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.ynzz.lab.chapter03.common.CandidateSql;

public class SqlSafetyEngine {
    private static final Set<String> WRITE_KEYWORDS = new HashSet<String>(Arrays.asList(
            "insert", "update", "delete", "drop", "alter", "truncate", "create", "replace", "merge"));

    public SqlSafetyDecision inspect(CandidateSql candidateSql, SchemaSnapshot snapshot) {
        String original = candidateSql.getValue().trim();
        String normalized = original.toLowerCase(Locale.ROOT).replaceAll("\\s+", " ");

        if (!normalized.startsWith("select ")) {
            return SqlSafetyDecision.block("WRITE_OPERATION_NOT_ALLOWED", original);
        }

        for (String keyword : WRITE_KEYWORDS) {
            if (containsWord(normalized, keyword)) {
                return SqlSafetyDecision.block("WRITE_OPERATION_NOT_ALLOWED", original);
            }
        }

        if (!containsAllowedTable(normalized, snapshot)) {
            return SqlSafetyDecision.block("TABLE_NOT_ALLOWED", original);
        }

        if (containsSensitiveColumn(normalized, snapshot)) {
            return SqlSafetyDecision.block("SENSITIVE_FIELD_NOT_ALLOWED", original);
        }

        String limitedSql = ensureLimit(original);
        return SqlSafetyDecision.allow(limitedSql);
    }

    private boolean containsWord(String normalized, String word) {
        return Pattern.compile("(^|[^a-z_])" + word + "([^a-z_]|$)").matcher(normalized).find();
    }

    private boolean containsAllowedTable(String normalized, SchemaSnapshot snapshot) {
        Matcher matcher = Pattern.compile("\\bfrom\\s+([a-zA-Z_][a-zA-Z0-9_]*)").matcher(normalized);
        while (matcher.find()) {
            if (!snapshot.isAllowedTable(matcher.group(1))) {
                return false;
            }
            return true;
        }
        return false;
    }

    private boolean containsSensitiveColumn(String normalized, SchemaSnapshot snapshot) {
        String[] tokens = normalized.split("[^a-zA-Z0-9_]+");
        for (int i = 0; i < tokens.length; i++) {
            if (snapshot.isSensitiveColumn(tokens[i])) {
                return true;
            }
        }
        return false;
    }

    private String ensureLimit(String sql) {
        String normalized = sql.toLowerCase(Locale.ROOT);
        if (Pattern.compile("\\blimit\\s+[0-9]+\\b").matcher(normalized).find()) {
            return sql;
        }
        return sql.replaceAll(";\\s*$", "") + " LIMIT 50";
    }
}

