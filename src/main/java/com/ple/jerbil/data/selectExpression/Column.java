package com.ple.jerbil.data.selectExpression;

import com.ple.jerbil.data.DelayedImmutable;
import com.ple.jerbil.data.query.Table;

/**
 *
 */
@DelayedImmutable
public interface Column <T extends Column> {

    static PartialColumn make(String name, Table table) {
        return PartialColumn.make(name, table);
    }

    public String getName();

    public T primary();

    public T indexed();

}
