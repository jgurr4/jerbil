package com.ple.jerbil.data.selectExpression;

import com.ple.jerbil.data.Immutable;
import com.ple.jerbil.data.selectExpression.booleanExpression.BooleanExpression;

/**
 * StringExpression is any expression that evaluates to a String. For example:
 * select "string", concat("hello","world") etc...
 * important note: "string" + "string" does not concat in mysql, use the concat() function instead.
 */
@Immutable
public class StringExpression extends OrderedExpression {

    protected StringExpression() {
    }

    public static StringExpression make() {
        return new StringExpression();
    }

    @Override
    public BooleanExpression eq(Expression item) {
        return null;
    }

}
