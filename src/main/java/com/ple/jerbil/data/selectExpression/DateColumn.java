package com.ple.jerbil.data.selectExpression;

import com.ple.jerbil.data.DataSpec;
import com.ple.jerbil.data.DataType;
import com.ple.jerbil.data.DateInterval;
import com.ple.jerbil.data.Immutable;
import com.ple.jerbil.data.selectExpression.booleanExpression.BooleanExpression;

@Immutable
public class DateColumn extends Column<DateColumn> implements DateExpression {

    protected DateColumn(String name, DataSpec dataSpec, boolean indexed, boolean primary, Expression generatedFrom, DateExpression defaultValue) {
        super(name, dataSpec, indexed, primary, generatedFrom, defaultValue);
    }

    @Override
    public DateColumn make(String name, DataSpec dataSpec, boolean indexed, boolean primary, Expression generatedFrom) {
        return new DateColumn(name, dataSpec, indexed, primary, generatedFrom, null);
    }

    public DateColumn defaultValue(DateExpression dateExp) {
        return new DateColumn(name, dataSpec, indexed, primary, generatedFrom, dateExp);
    }

    public static DateColumn make(String name, DataSpec dataSpec) {
        return new DateColumn(name, dataSpec, false, false, null, null);
    }

    public static DateColumn make(String name, Boolean indexed, Boolean primary) {
        return new DateColumn(name, DataSpec.make(DataType.datetime), indexed, primary, null, null);
    }

    public static DateColumn make(String name, DataSpec dataSpec, Boolean indexed, Boolean primary) {
        return new DateColumn(name, dataSpec, indexed, primary, null, null);
    }

    @Override
    public DateExpression plus(DateInterval dateInterval) {
        return null;
    }

    @Override
    public DateExpression minus(DateInterval dateInterval) {
        return null;
    }

    @Override
    public BooleanExpression isGreaterThan(Expression i) {
        return null;
    }

    @Override
    public BooleanExpression isLessThan(Expression i) {
        return null;
    }

    @Override
    public BooleanExpression eq(Expression item) {
        return null;
    }

}
