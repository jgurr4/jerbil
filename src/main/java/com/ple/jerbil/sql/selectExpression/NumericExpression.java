package com.ple.jerbil.sql.selectExpression;

import com.ple.jerbil.sql.Immutable;
import com.ple.jerbil.sql.query.CompleteQuery;
import com.ple.jerbil.sql.selectExpression.booleanExpression.BooleanExpression;

/**
 * NumberExpression is any expression that evaluates to a numeric value. For example:
 * select 2+2, col1 + col2, 3*3, now() - 10 etc...
 */
@Immutable
public class NumericExpression implements Expression {

    public final int value;

    protected NumericExpression(int value) {
        this.value = value;
    }

    public static NumericExpression make(int value) {
        return new NumericExpression(value);
    }

    public NumericExpression plus(NumericExpression i) {
       return null;
    }

    public NumericExpression minus(NumericExpression i) {
        return null;
    }

    public NumericExpression times(NumericExpression i) {
        return null;
    }

    public NumericExpression dividedBy(NumericExpression i) {
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

    @Override
    public CompleteQuery select() {
        return null;
    }
}
