package com.ple.jerbil.data.selectExpression;

import com.ple.jerbil.data.selectExpression.booleanExpression.BooleanExpression;
import com.ple.jerbil.data.selectExpression.booleanExpression.GreaterThan;

/**
 * This is parent of all expressions that can be compared with > or <.
 */
public abstract class OrderedExpression implements Expression {
    @Override
    public GreaterThan isGreaterThan(Expression i) {
        return GreaterThan.make(this, i);
    }

    @Override
    public BooleanExpression isLessThan(Expression i) {
        return null;
    }

}
