package com.ple.jerbil.sql.selectExpression;

import com.ple.jerbil.sql.DelayedImmutable;
import com.ple.jerbil.sql.query.Table;

/**
 *
 */
@DelayedImmutable
public interface Column <T extends Column> {

    static PartialColumn make(String name, Table table) {
        return PartialColumn.make(name, table);
    }

    public abstract T primary();

    public abstract T indexed();

}
