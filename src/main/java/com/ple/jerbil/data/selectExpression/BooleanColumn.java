package com.ple.jerbil.data.selectExpression;

import com.ple.jerbil.data.DataSpec;
import com.ple.jerbil.data.DataType;
import com.ple.jerbil.data.query.Table;
import com.ple.jerbil.data.selectExpression.booleanExpression.BooleanExpression;
import org.jetbrains.annotations.Nullable;

/**
 * BooleanColumn is for compile-time checking to ensure people use a BooleanColumn in the
 * rare cases where a booleanExpression is a Boolean Column. For example: Select * from table where isTrue; // isTrue could be a column with boolean values.
 */
public class BooleanColumn extends Column<BooleanColumn> implements BooleanExpression  {

    protected BooleanColumn(String name, Table table, DataSpec dataSpec, boolean indexed, boolean primary, @Nullable Expression generatedFrom, @Nullable BooleanExpression defaultValue) {
        super(name, table, dataSpec, indexed, primary, generatedFrom, defaultValue);
    }

    @Override
    public BooleanColumn make(String name, DataSpec dataSpec, boolean indexed, boolean primary, Expression generatedFrom) {
        return new BooleanColumn(name, table, dataSpec, indexed, primary, generatedFrom, (BooleanExpression) defaultValue);
    }

    public static Column make(String name, Table table, DataSpec dataSpec) {
        return new BooleanColumn(name, table, dataSpec, false, false, null, null);
    }

    public static Column make(String name, Table table, Boolean indexed, Boolean primary) {
        return new BooleanColumn(name, table, DataSpec.make(DataType.bool), indexed, primary, null, null);
    }

    public static Column make(String name, Table table, DataSpec dataSpec, Boolean indexed, Boolean primary) {
        return new BooleanColumn(name, table, dataSpec, indexed, primary, null, null);
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

    public BooleanColumn defaultValue(BooleanExpression bool) {
        return new BooleanColumn(columnName, table, dataSpec, indexed, primary, generatedFrom, bool);
    }

}
