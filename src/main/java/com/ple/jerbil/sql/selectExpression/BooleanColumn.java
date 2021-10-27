package com.ple.jerbil.sql.selectExpression;

import com.ple.jerbil.sql.DataSpec;
import com.ple.jerbil.sql.DataType;
import com.ple.jerbil.sql.query.Table;
import com.ple.jerbil.sql.selectExpression.booleanExpression.BooleanExpression;

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
        return new BooleanColumn(name, table, DataSpec.make(DataType.tinyint, 1), false, false);
    }

    @Override
    public BooleanColumn primary() {
        return new BooleanColumn(this.name, this.table, this.dataSpec, this.indexed, true);
    }

    @Override
    public BooleanColumn indexed() {
        return new BooleanColumn(this.name, this.table, this.dataSpec, true, this.primary);
    }

    public static Column make(String name, Table table, Boolean indexed, Boolean primary) {
        return new BooleanColumn(name, table, DataSpec.make(DataType.bool), indexed, primary);
    }

    public static Column make(String name, Table table, DataSpec dataSpec, Boolean indexed, Boolean primary) {
        return new BooleanColumn(name, table, dataSpec, indexed, primary);
    }
}
