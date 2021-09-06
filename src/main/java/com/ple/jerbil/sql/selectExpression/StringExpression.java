package com.ple.jerbil.sql.selectExpression;

/**
 * StringExpression is any expression that evaluates to a String. For example:
 * select "string", concat("hello","world") etc...
 * important note: "string" + "string" does not concat in mysql, use the concat() function instead.
 */
public class StringExpression implements Expression {

}
