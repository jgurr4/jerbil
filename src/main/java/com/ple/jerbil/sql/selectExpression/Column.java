package com.ple.jerbil.sql.selectExpression;

import com.ple.jerbil.sql.DataSpec;
import com.ple.jerbil.sql.DelayedImmutable;
import com.ple.jerbil.sql.fromExpression.Table;
import com.ple.jerbil.sql.selectExpression.booleanExpression.BooleanExpression;
import org.jetbrains.annotations.Nullable;

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
