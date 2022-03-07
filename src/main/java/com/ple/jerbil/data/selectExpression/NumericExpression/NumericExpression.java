package com.ple.jerbil.data.selectExpression.NumericExpression;

import com.ple.util.Immutable;
import com.ple.jerbil.data.selectExpression.OrderedExpression;
import com.ple.jerbil.data.selectExpression.booleanExpression.*;

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

    default BooleanExpression isEqualTo(NumericExpression numExp) {
        return null;
    }

    default BooleanExpression isNotEqualTo(NumericExpression numExp) {
        return null;
    }

    default BooleanExpression isGreaterThan(NumericExpression numExp) {
        return GreaterThan.make(this, numExp);
    }

    default BooleanExpression isGreaterThanOrEqual(NumericExpression numExp) {
        return null;
    }

    default BooleanExpression isLessThan(NumericExpression numExp) {
        return null;
    }

    default BooleanExpression isLessThanOrEqual(NumericExpression numExp) {
        return null;
    }

    default BooleanExpression isBetween(LiteralNumber n1, LiteralNumber n2) {
        return Between.make(this, n1, n2);
    }

    default BooleanExpression isNotBetween(LiteralNumber n1, LiteralNumber n2) {
        return NotBetween.make(this, n1, n2);
    }

    default BooleanExpression eq(NumericExpression numExp) {
        return Equals.make(this, numExp);
    }

    default BooleanExpression ne(NumericExpression numExp) {
        return null;
    }

    default BooleanExpression gt(NumericExpression numExp) {
        return GreaterThan.make(this, numExp);
    }

    default BooleanExpression ge(NumericExpression numExp) {
        return GreaterOrEqual.make(this, numExp);
    }

    default BooleanExpression lt(NumericExpression numExp) {
        return null;
    }

    default BooleanExpression le(NumericExpression numExp) {
        return null;
    }

}
