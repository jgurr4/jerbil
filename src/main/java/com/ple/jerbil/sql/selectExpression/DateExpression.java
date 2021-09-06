package com.ple.jerbil.sql.selectExpression;

/**
 * DateExpression is any expression which evaluates to a date. For example:
 * select now(), '02-03-1995'; are both date expressions.
 * Mixing rules for DateExpression are different in some ways For example: select 05-23-1990 + 5;
 * is allowed and will return an int representation of the date. But select * from table where date > true; is not allowed.
 * So you can add int to date, but you cannot add date to date, and you should be able to compare date to date with =, > or <.
 * But you cannot compare date to boolean etc.. these rules are set and determined for each class within their methods.
 */
public class DateExpression implements Expression {

}
