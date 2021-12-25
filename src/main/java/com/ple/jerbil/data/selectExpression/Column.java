package com.ple.jerbil.data.selectExpression;

import com.ple.jerbil.data.DelayedImmutable;
import com.ple.jerbil.data.query.Table;

@DelayedImmutable
public interface Column <T extends Column> {

    static PartialColumn make(String name, Table table) {
        return PartialColumn.make(name, table);
    }

    String getName();

    Table getTable();

    T primary();

    T indexed();

    boolean isPrimary();

    boolean isIndexed();
}
