package com.ple.jerbil.data.selectExpression;

import com.ple.jerbil.data.DataSpec;
import com.ple.jerbil.data.DataType;
import com.ple.jerbil.data.selectExpression.booleanExpression.BooleanExpression;
import com.ple.jerbil.data.selectExpression.booleanExpression.Equals;
import com.ple.jerbil.data.selectExpression.booleanExpression.GreaterThan;

public class StringColumn extends Column<StringColumn> implements StringExpression {

    protected StringColumn(String name, DataSpec dataSpec, boolean indexed, boolean primary, Expression generatedFrom, StringExpression defaultValue) {
        super(name, dataSpec, indexed, primary, generatedFrom, defaultValue);
    }

    @Override
    public StringColumn make(String name, DataSpec dataSpec, boolean indexed, boolean primary, Expression generatedFrom) {
        return new StringColumn(name, dataSpec, indexed, primary, generatedFrom, null);
    }

    public static StringColumn make(String name, DataSpec dataSpec, boolean indexed) {
        return new StringColumn(name, dataSpec, indexed, false, null, null);
    }

    public static StringColumn make(String name, DataSpec dataSpec) {
        return new StringColumn(name, dataSpec, false, false, null, null);
    }

    public static StringColumn make(String name, int size) {
        return new StringColumn(name, DataSpec.make(DataType.varchar, size), false, false, null, null);
    }

    public static StringColumn make(String name, Boolean indexed, Boolean primary) {
        return new StringColumn(name, DataSpec.make(DataType.varchar), indexed, primary, null, null);
    }

    public static StringColumn make(String name, DataSpec dataSpec, Boolean indexed, Boolean primary) {
        return new StringColumn(name, dataSpec, indexed, primary, null, null);
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
        return new StringColumn(name, dataSpec, indexed, primary, generatedFrom, str);
    }

}
