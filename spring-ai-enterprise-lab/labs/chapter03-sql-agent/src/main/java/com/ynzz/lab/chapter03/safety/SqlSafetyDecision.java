package com.ynzz.lab.chapter03.safety;

public class SqlSafetyDecision {
    private final boolean allowed;
    private final String reason;
    private final String safeSql;

    private SqlSafetyDecision(boolean allowed, String reason, String safeSql) {
        this.allowed = allowed;
        this.reason = reason;
        this.safeSql = safeSql;
    }

    public static SqlSafetyDecision allow(String safeSql) {
        return new SqlSafetyDecision(true, "ALLOW_SELECT", safeSql);
    }

    public static SqlSafetyDecision block(String reason, String sql) {
        return new SqlSafetyDecision(false, reason, sql);
    }

    public boolean isAllowed() {
        return allowed;
    }

    public String getReason() {
        return reason;
    }

    public String getSafeSql() {
        return safeSql;
    }
}

