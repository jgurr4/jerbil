package com.ple.jerbil.data.selectExpression;

import com.ple.jerbil.data.DateInterval;
import com.ple.jerbil.data.Immutable;

/**
 * DateExpression is any expression which evaluates to a date. For example:
 * select now(), '02-03-1995'; are both date expressions.
 * Mixing rules for DateExpression are different in some ways For example: select 05-23-1990 + 5;
 * is allowed and will return an int representation of the date. But select * from table where date > true; is not allowed.
 * So you can add int to date, but you cannot add date to date, and you should be able to compare date to date with =, > or <.
 * But you cannot compare date to boolean etc.. these rules are set and determined for each class within their methods.
 * UPDATE: You may add intervals or numeric values to DateExpressions.
 * Example #1: select now(), now() + 10000;  // The result is a 10000 second difference between the two returned columns.
 * Example #2: select now(), now() + interval 1 day;  //The result is 1 day difference between the two returned columns.
 */
@Immutable
public interface DateExpression extends OrderedExpression {

/*
    public DateExpression plus(DateInterval dateInterval);

    public DateExpression minus(DateInterval dateInterval);
*/

}
