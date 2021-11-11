package com.ple.jerbil.data.selectExpression;

import com.ple.jerbil.data.selectExpression.booleanExpression.BooleanExpression;

/**
 * This is parent of all expressions that can be compared with > or <.
 */
public abstract class OrderedExpression implements Expression {
    @Override
    public BooleanExpression isGreaterThan(Expression i) {
        return null;
    }

    @Override
    public BooleanExpression isLessThan(Expression i) {
        return null;
    }

}
