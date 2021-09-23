package com.ple.jerbil.sql.selectExpression;

import com.ple.jerbil.sql.Immutable;
import com.ple.jerbil.sql.query.CompleteQuery;
import com.ple.jerbil.sql.selectExpression.booleanExpression.BooleanExpression;

/**
 * StringExpression is any expression that evaluates to a String. For example:
 * select "string", concat("hello","world") etc...
 * important note: "string" + "string" does not concat in mysql, use the concat() function instead.
 */
@Immutable
public class StringExpression implements Expression {

    public final String value;

    protected StringExpression(String value) {
        this.value = value;
    }

    public static StringExpression make(String value) {
        return new StringExpression(value);
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
