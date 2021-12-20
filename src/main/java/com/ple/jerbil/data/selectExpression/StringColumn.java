package com.ple.jerbil.data.selectExpression;

import com.ple.jerbil.data.DataSpec;
import com.ple.jerbil.data.DataType;
import com.ple.jerbil.data.query.Table;
import com.ple.jerbil.data.selectExpression.booleanExpression.Equals;
import com.ple.jerbil.data.selectExpression.booleanExpression.GreaterThan;

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
        final StringColumn stringColumn = new StringColumn(name, table, DataSpec.make(DataType.varchar), false, false);
        table.add(stringColumn);
        return stringColumn;
    }

    public static StringColumn make(String name, Table table, int size) {
        final StringColumn stringColumn = new StringColumn(name, table, DataSpec.make(DataType.varchar, size), false, false);
        table.add(stringColumn);
        return stringColumn;
    }

    public static StringColumn make(String name, Table table, Boolean indexed, Boolean primary) {
        final StringColumn stringColumn = new StringColumn(name, table, DataSpec.make(DataType.varchar), indexed, primary);
        table.add(stringColumn);
        return stringColumn;
    }

    public static StringColumn make(String name, Table table, DataSpec dataSpec, Boolean indexed, Boolean primary) {
        final StringColumn stringColumn = new StringColumn(name, table, dataSpec, indexed, primary);
        table.add(stringColumn);
        return stringColumn;
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
    public StringColumn primary() {
        return new StringColumn(this.name, this.table, this.dataSpec, this.indexed, true);
    }

    @Override
    public StringColumn indexed() {
        return new StringColumn(this.name, this.table, this.dataSpec, true, this.primary);
    }

    public Equals eq(Expression value) {
        return Equals.make(this, value);
    }

    public Equals eq(String value) {
        return Equals.make(this, Literal.make(value));
    }

    public GreaterThan isGreaterThan(Expression value) {
        return GreaterThan.make(this, value);
    }
}
