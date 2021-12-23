package com.ple.jerbil.data.selectExpression.NumericExpression;

import com.ple.jerbil.data.Immutable;
import com.ple.jerbil.data.selectExpression.Expression;
import com.ple.jerbil.data.selectExpression.OrderedExpression;
import com.ple.jerbil.data.selectExpression.booleanExpression.BooleanExpression;

/**
 * NumberExpression is any expression that evaluates to a numeric value. For example:
 * select 2+2, col1 + col2, 3*3, now() - 10 etc...
 */
@Immutable
public class NumericExpression extends OrderedExpression {

    protected NumericExpression() {
    }

    public NumericExpression plus(NumericExpression i) {
        return ArithmeticExpression.make(this, i, Operator.plus);
    }

    public NumericExpression minus(NumericExpression i) {
        return ArithmeticExpression.make(this, i, Operator.minus);
    }

    public NumericExpression times(NumericExpression i) {
        return ArithmeticExpression.make(this, i, Operator.times);
    }

    public NumericExpression dividedBy(NumericExpression i) {
        return ArithmeticExpression.make(this, i, Operator.dividedby);
    }

    @Override
    public BooleanExpression eq(Expression item) {
        return null;
    }

}
