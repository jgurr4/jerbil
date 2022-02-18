package com.ple.jerbil.data.selectExpression;

import com.ple.jerbil.data.Immutable;
import com.ple.jerbil.data.selectExpression.booleanExpression.Equals;

/**
 * StringExpression is any expression that evaluates to a String. For example:
 * select "string", concat("hello","world") etc...
 * important note: "string" + "string" does not concat in mysql, use the concat() function instead.
 */
@Immutable
public interface StringExpression extends OrderedExpression {

}
