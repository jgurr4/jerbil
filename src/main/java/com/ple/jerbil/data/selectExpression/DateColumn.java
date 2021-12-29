package com.ple.jerbil.data.selectExpression;

import com.ple.jerbil.data.DataSpec;
import com.ple.jerbil.data.DataType;
import com.ple.jerbil.data.DateInterval;
import com.ple.jerbil.data.Immutable;
import com.ple.jerbil.data.query.Table;
import com.ple.jerbil.data.selectExpression.booleanExpression.BooleanExpression;

@Immutable
public class DateColumn extends Column<DateColumn> implements DateExpression {

    protected DateColumn(String name, Table table, DataSpec dataSpec, boolean indexed, boolean primary, Expression generatedFrom) {
        super(name, table, dataSpec, indexed, primary, generatedFrom);
    }

    @Override
    public DateColumn make(String name, Table table, DataSpec dataSpec, boolean indexed, boolean primary, Expression generatedFrom) {
        return new DateColumn(name, table, dataSpec, indexed, primary, generatedFrom);
    }

    public static DateColumn make(String name, Table table, DataSpec dataSpec) {
        return new DateColumn(name, table, dataSpec, false, false, null);
    }

    public static DateColumn make(String name, Table table, Boolean indexed, Boolean primary) {
        return new DateColumn(name, table, DataSpec.make(DataType.datetime), indexed, primary, null);
    }

    public static DateColumn make(String name, Table table, DataSpec dataSpec, Boolean indexed, Boolean primary) {
        return new DateColumn(name, table, dataSpec, indexed, primary, null);
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
