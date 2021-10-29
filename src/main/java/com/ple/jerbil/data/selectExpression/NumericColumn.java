package com.ple.jerbil.data.selectExpression;

import com.ple.jerbil.data.DataSpec;
import com.ple.jerbil.data.DataType;
import com.ple.jerbil.data.query.Table;

public class NumericColumn extends NumericExpression implements Column<NumericColumn> {

    public final String name;
    public final Table table;
    public final DataSpec dataSpec;
    public final boolean indexed;
    public final boolean primary;

    protected NumericColumn(String name, Table table, DataSpec dataSpec, boolean indexed, boolean primary) {
        this.name = name;
        this.table = table;
        this.dataSpec = dataSpec;
        this.indexed = indexed;
        this.primary = primary;
    }

    public static Column make(String name, Table table, int size) {
        return new NumericColumn(name, table, DataSpec.make(DataType.integer, size), false, false);
    }

    public static NumericColumn make(String name, Table table) {
        return new NumericColumn(name, table, DataSpec.make(DataType.integer), false, false);
    }

    public static NumericColumn make(String name, Table table, boolean primary) {
        return new NumericColumn(name, table, DataSpec.make(DataType.integer), false, primary);
    }

    @Override
    public NumericColumn primary() {
        return new NumericColumn(this.name, this.table, this.dataSpec, this.indexed, true);
    }

    @Override
    public NumericColumn indexed() {
        return new NumericColumn(this.name, this.table, this.dataSpec, true, this.primary);
    }

    public static Column make(String name, Table table, Boolean indexed, Boolean primary) {
        return new NumericColumn(name, table, DataSpec.make(DataType.integer), indexed, primary);
    }

    public static Column make(String name, Table table, DataSpec dataSpec, Boolean indexed, Boolean primary) {
        return new NumericColumn(name, table, dataSpec, indexed, primary);
    }

}
