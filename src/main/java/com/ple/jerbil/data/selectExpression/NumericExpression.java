package com.ple.jerbil.data.selectExpression;

import com.ple.jerbil.data.Immutable;
import com.ple.jerbil.data.selectExpression.booleanExpression.BooleanExpression;

/**
 * NumberExpression is any expression that evaluates to a numeric value. For example:
 * select 2+2, col1 + col2, 3*3, now() - 10 etc...
 */
@Immutable
public class NumericExpression extends OrderedExpression {

    protected NumericExpression() {
    }

    public static NumericExpression make() {
        return new NumericExpression();
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
    public BooleanExpression eq(Expression item) {
        return null;
    }

}
