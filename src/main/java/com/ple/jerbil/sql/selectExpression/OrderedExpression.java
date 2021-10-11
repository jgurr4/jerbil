package com.ple.jerbil.sql.selectExpression;

import com.ple.jerbil.sql.selectExpression.booleanExpression.BooleanExpression;

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
