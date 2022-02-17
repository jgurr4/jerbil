package com.ple.jerbil.data.selectExpression;

import com.ple.jerbil.data.DataSpec;
import com.ple.jerbil.data.DataType;
import com.ple.jerbil.data.query.Table;
import com.ple.jerbil.data.selectExpression.booleanExpression.BooleanExpression;
import com.ple.jerbil.data.selectExpression.booleanExpression.Equals;
import com.ple.jerbil.data.selectExpression.booleanExpression.GreaterThan;

public class StringColumn extends Column<StringColumn> implements StringExpression {

    protected StringColumn(String name, Table table, DataSpec dataSpec, Expression generatedFrom, StringExpression defaultValue) {
        super(name, table, dataSpec, generatedFrom, defaultValue);
    }

    @Override
    public StringColumn make(String name, DataSpec dataSpec, Expression generatedFrom) {
        return new StringColumn(name, table, dataSpec, generatedFrom, (StringExpression) defaultValue);
    }

    public static StringColumn make(String name, Table table, int size) {
        return new StringColumn(name, table, DataSpec.make(DataType.varchar, size), null, null);
    }

    public static StringColumn make(String name, Table table) {
        return new StringColumn(name, table, DataSpec.make(DataType.varchar), null, null);
    }

    public static StringColumn make(String name, Table table, DataSpec dataSpec) {
        return new StringColumn(name, table, dataSpec, null, null);
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

    public StringColumn defaultValue(StringExpression str) {
        return new StringColumn(columnName, table, dataSpec, generatedFrom, str);
    }

}
