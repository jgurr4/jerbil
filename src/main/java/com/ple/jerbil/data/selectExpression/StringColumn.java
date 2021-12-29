package com.ple.jerbil.data.selectExpression;

import com.ple.jerbil.data.DataSpec;
import com.ple.jerbil.data.DataType;
import com.ple.jerbil.data.query.Table;
import com.ple.jerbil.data.selectExpression.booleanExpression.BooleanExpression;
import com.ple.jerbil.data.selectExpression.booleanExpression.Equals;
import com.ple.jerbil.data.selectExpression.booleanExpression.GreaterThan;

public class StringColumn extends Column<StringColumn> implements StringExpression {

    protected StringColumn(String name, Table table, DataSpec dataSpec, boolean indexed, boolean primary) {
        super(name, table, dataSpec, indexed, primary);
    }

    @Override
    public StringColumn make(String name, Table table, DataSpec dataSpec, boolean indexed, boolean primary) {
        return new StringColumn(name, table, dataSpec, indexed, primary);
    }

    public static StringColumn make(String name, Table table, DataSpec dataSpec, boolean indexed) {
        return new StringColumn(name, table, dataSpec, indexed, false);
    }

    public static StringColumn make(String name, Table table, DataSpec dataSpec) {
        return new StringColumn(name, table, dataSpec, false, false);
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

    public Equals eq(Expression value) {
        return Equals.make(this, value);
    }

    public Equals eq(String value) {
        return Equals.make(this, Literal.make(value));
    }

    public GreaterThan isGreaterThan(Expression value) {
        return GreaterThan.make(this, value);
    }

    @Override
    public BooleanExpression isLessThan(Expression i) {
        return null;
    }

}
