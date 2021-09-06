package com.ple.jerbil.sql.selectExpression;

import com.ple.jerbil.sql.Immutable;
import com.ple.jerbil.sql.selectExpression.booleanExpression.BooleanExpression;
import com.ple.jerbil.sql.selectExpression.booleanExpression.Equals;

/**
 * Expression is more specific than SelectExpression because it doesn't include AliasedExpressions.
 * Expression includes = > < >= <= <> != [NOT] LIKE, [NOT] BETWEEN, [NOT] REGEXP, IS [NOT] NULL, IN, ANY, ALL, SOME, SOUNDS LIKE etc...
 * see: https://dev.mysql.com/doc/refman/8.0/en/expressions.html
 * if() is also a class that exists under expression even though it returns a boolean.
 * DateExpression, NumberExpression, StringExpression, BooleanExpression will extend this class.
 */
@Immutable
public interface Expression extends SelectExpression {

  public AliasedExpression as(String name) {
    return AliasedExpression.make(name);
  }

  public Expression times(Expression i) {

    return null;
  }

  public Expression minus(Expression i) {

    return null;
  }

  public Expression plus(Expression i) {

    return null;
  }

  public BooleanExpression isGreaterThan(Expression i) {

    return null;
  }

  public Expression dividedBy(Expression i) {

    return null;
  }

  public BooleanExpression eq(String item) {
    return Equals.make(this, Literal.make(item));
  }

}
