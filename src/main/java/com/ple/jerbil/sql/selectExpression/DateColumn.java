package com.ple.jerbil.sql.selectExpression;

import com.ple.jerbil.sql.DataSpec;
import com.ple.jerbil.sql.DataType;
import com.ple.jerbil.sql.Immutable;
import com.ple.jerbil.sql.fromExpression.Table;

@Immutable
public class DateColumn extends DateExpression implements Column<DateColumn> {

    public final String name;
    public final Table table;
    public final DataSpec dataSpec;
    public final boolean indexed;
    public final boolean primary;

    protected DateColumn(String name, Table table, DataSpec dataSpec, boolean indexed, boolean primary) {
        this.name = name;
        this.table = table;
        this.dataSpec = dataSpec;
        this.indexed = indexed;
        this.primary = primary;
    }

    public static Column make(String name, Table table) {
        return new DateColumn(name, table, DataSpec.make(DataType.datetime), false, false);
    }

    @Override
    public DateColumn primary() {
        return new DateColumn(this.name, this.table, this.dataSpec, this.indexed, true);
    }

    @Override
    public DateColumn indexed() {
        return new DateColumn(this.name, this.table, this.dataSpec, true, this.primary);
    }

    public static Column make(String name, Table table, Boolean indexed, Boolean primary) {
        return new DateColumn(name, table, DataSpec.make(DataType.datetime), indexed, primary);
    }

    public static Column make(String name, Table table, DataSpec dataSpec, Boolean indexed, Boolean primary) {
        return new DateColumn(name, table, dataSpec, indexed, primary);
    }
}
