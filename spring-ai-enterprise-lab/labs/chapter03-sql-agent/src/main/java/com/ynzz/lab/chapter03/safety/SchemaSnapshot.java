package com.ynzz.lab.chapter03.safety;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class SchemaSnapshot {
    private final Set<String> allowedTables = new HashSet<String>();
    private final Set<String> allowedColumns = new HashSet<String>();
    private final Set<String> sensitiveColumns = new HashSet<String>();

    public static SchemaSnapshot orderReport() {
        SchemaSnapshot snapshot = new SchemaSnapshot();
        snapshot.allowedTables.add("order_report");
        snapshot.allowedColumns.addAll(Arrays.asList(
                "id",
                "order_id",
                "product_name",
                "customer_level",
                "order_month",
                "amount",
                "status",
                "created_at"));
        snapshot.sensitiveColumns.addAll(Arrays.asList("mobile", "email", "id_card", "customer_mobile"));
        return snapshot;
    }

    public boolean isAllowedTable(String table) {
        return allowedTables.contains(table.toLowerCase());
    }

    public boolean isAllowedColumn(String column) {
        return allowedColumns.contains(column.toLowerCase());
    }

    public boolean isSensitiveColumn(String column) {
        return sensitiveColumns.contains(column.toLowerCase());
    }
}

