package com.ple.jerbil.data.selectExpression;

import com.ple.jerbil.data.Immutable;

/**
 * AliasedExpression is any expression which is given an alias. For example:
 * select 2+2 as result, col1 + col2 as total, concat(first_name," ", last_name) as full_name; etc...
 * Sometimes tables, joins, and subqueries are given aliases as well.
 */
@Immutable
public class AliasedExpression implements SelectExpression {
  public final String alias;
  public final CountAgg countAgg;
  public final Expression expression;

  protected AliasedExpression(String alias, CountAgg countAgg, Expression expression) {
    this.alias = alias;
    this.countAgg = countAgg;
    this.expression = expression;
  }

  public static AliasedExpression make(String alias, CountAgg countAgg) {
    return new AliasedExpression(alias, countAgg, null);
  }

  public static AliasedExpression make(String alias, Expression expression) {
    return new AliasedExpression(alias, null, expression);
  }

  public static AliasedExpression make(String alias) {
    return new AliasedExpression(alias, null, null);
  }

}
