package com.ple.jerbil.data.selectExpression.NumericExpression;

import com.ple.jerbil.data.Immutable;
import com.ple.jerbil.data.selectExpression.OrderedExpression;

/**
 * NumberExpression is any expression that evaluates to a numeric value. For example:
 * select 2+2, col1 + col2, 3*3, now() - 10 etc...
 */
@Immutable
public interface NumericExpression extends OrderedExpression {

    default NumericExpression plus(NumericExpression i) {
        return ArithmeticExpression.make(this, i, Operator.plus);
    }

    default NumericExpression minus(NumericExpression i) {
        return ArithmeticExpression.make(this, i, Operator.minus);
    }

    default NumericExpression times(NumericExpression i) {
        return ArithmeticExpression.make(this, i, Operator.times);
    }

    default NumericExpression dividedBy(NumericExpression i) {
        return ArithmeticExpression.make(this, i, Operator.dividedby);
    }

}
