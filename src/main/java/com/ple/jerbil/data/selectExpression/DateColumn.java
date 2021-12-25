package com.ple.jerbil.data.selectExpression;

import com.ple.jerbil.data.DataSpec;
import com.ple.jerbil.data.DataType;
import com.ple.jerbil.data.Immutable;
import com.ple.jerbil.data.query.Table;

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
        final DateColumn dateColumn = new DateColumn(name, table, DataSpec.make(DataType.datetime), false, false);
        table.add(dateColumn);
        return dateColumn;
    }

    public static Column make(String name, Table table, Boolean indexed, Boolean primary) {
        final DateColumn dateColumn = new DateColumn(name, table, DataSpec.make(DataType.datetime), indexed, primary);
        table.add(dateColumn);
        return dateColumn;
    }

    public static Column make(String name, Table table, DataSpec dataSpec, Boolean indexed, Boolean primary) {
        final DateColumn dateColumn = new DateColumn(name, table, dataSpec, indexed, primary);
        table.add(dateColumn);
        return dateColumn;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public Table getTable() {
        return this.table;
    }

    @Override
    public DateColumn primary() {
        return new DateColumn(this.name, this.table, this.dataSpec, this.indexed, true);
    }

    @Override
    public DateColumn indexed() {
        return new DateColumn(this.name, this.table, this.dataSpec, true, this.primary);
    }

    @Override
    public boolean isPrimary() {
        return primary;
    }

    @Override
    public boolean isIndexed() {
        return indexed;
    }

}
