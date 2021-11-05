package com.ple.jerbil.data.selectExpression;

import com.ple.jerbil.data.Immutable;
import com.ple.jerbil.data.selectExpression.booleanExpression.BooleanExpression;
import com.ple.jerbil.data.selectExpression.booleanExpression.Equals;

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
    public Equals eq(Expression item) {
        return Equals.make(this, item);
    }

}
