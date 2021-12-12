package com.ple.jerbil.data.selectExpression;

import com.ple.jerbil.data.DataSpec;
import com.ple.jerbil.data.DataType;
import com.ple.jerbil.data.query.Table;
import com.ple.jerbil.data.selectExpression.booleanExpression.BooleanExpression;

/**
 * BooleanColumn is for compile-time checking to ensure people use a BooleanColumn in the
 * rare cases where a booleanExpression is a Boolean Column. For example: Select * from table where isTrue; // isTrue could be a column with boolean values.
 */
public class BooleanColumn extends BooleanExpression implements Column<BooleanColumn> {

    public final String name;
    public final Table table;
    public final DataSpec dataSpec;
    public final boolean indexed;
    public final boolean primary;

    protected BooleanColumn(String name, Table table, DataSpec dataSpec, boolean indexed, boolean primary) {
        this.name = name;
        this.table = table;
        this.dataSpec = dataSpec;
        this.indexed = indexed;
        this.primary = primary;
    }

    public static Column make(String name, Table table) {
        final BooleanColumn booleanColumn = new BooleanColumn(name, table, DataSpec.make(DataType.tinyint, 1), false, false);
        table.add(booleanColumn);
        return booleanColumn;
    }

    public static Column make(String name, Table table, Boolean indexed, Boolean primary) {
        final BooleanColumn booleanColumn = new BooleanColumn(name, table, DataSpec.make(DataType.bool), indexed, primary);
        table.add(booleanColumn);
        return booleanColumn;
    }

    public static Column make(String name, Table table, DataSpec dataSpec, Boolean indexed, Boolean primary) {
        final BooleanColumn booleanColumn = new BooleanColumn(name, table, dataSpec, indexed, primary);
        table.add(booleanColumn);
        return booleanColumn;
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
    public BooleanColumn primary() {
        return new BooleanColumn(this.name, this.table, this.dataSpec, this.indexed, true);
    }

    @Override
    public BooleanColumn indexed() {
        return new BooleanColumn(this.name, this.table, this.dataSpec, true, this.primary);
    }
}
