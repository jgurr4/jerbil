package com.ple.jerbil.data.selectExpression;

import com.ple.jerbil.data.DataSpec;
import com.ple.jerbil.data.DataType;
import com.ple.jerbil.data.query.Table;
import com.ple.jerbil.data.selectExpression.booleanExpression.BooleanExpression;

public class StringColumn extends StringExpression implements Column<StringColumn> {

    public final String name;
    public final Table table;
    public final DataSpec dataSpec;
    public final boolean indexed;
    public final boolean primary;

    protected StringColumn(String name, Table table, DataSpec dataSpec, boolean indexed, boolean primary) {
        this.name = name;
        this.table = table;
        this.dataSpec = dataSpec;
        this.indexed = indexed;
        this.primary = primary;
    }

    public static StringColumn make(String name, Table table) {
        return new StringColumn(name, table, DataSpec.make(DataType.varchar), false, false);
    }

    @Override
    public StringColumn primary() {
        return new StringColumn(this.name, this.table, this.dataSpec, this.indexed, true);
    }

    @Override
    public StringColumn indexed() {
        return new StringColumn(this.name, this.table, this.dataSpec, true, this.primary);
    }

    public static StringColumn make(String name, Table table, int size) {
        return new StringColumn(name, table, DataSpec.make(DataType.varchar, size), false, false);
    }

    public static StringColumn make(String name, Table table, Boolean indexed, Boolean primary) {
        return new StringColumn(name, table, DataSpec.make(DataType.varchar), indexed, primary);
    }

    public static StringColumn make(String name, Table table, DataSpec dataSpec, Boolean indexed, Boolean primary) {
        return new StringColumn(name, table, dataSpec, indexed, primary);
    }

    public BooleanExpression eq(String value) {
        return BooleanExpression.make();
    }
}
