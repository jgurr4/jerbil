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

    protected BooleanColumn(String name, Table table, DataSpec dataSpec, @Nullable Expression generatedFrom, @Nullable BooleanExpression defaultValue) {
        super(name, table, dataSpec, generatedFrom, defaultValue);
    }

    @Override
    public BooleanColumn make(String name, DataSpec dataSpec, Expression generatedFrom) {
        return new BooleanColumn(name, table, dataSpec, generatedFrom, (BooleanExpression) defaultValue);
    }

    public static Column make(String name, Table table, DataSpec dataSpec) {
        return new BooleanColumn(name, table, dataSpec, null, null);
    }

    public static Column make(String name, Table table) {
        return new BooleanColumn(name, table, DataSpec.make(DataType.bool), null, null);
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
        return new BooleanColumn(columnName, table, dataSpec, generatedFrom, bool);
    }

}
