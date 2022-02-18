package com.ple.jerbil.data.selectExpression;

import com.ple.jerbil.data.Immutable;
import com.ple.jerbil.data.selectExpression.booleanExpression.BooleanExpression;

/**
 * Expression is more specific than SelectExpression because it doesn't include AliasedExpressions.
 * Expression includes = > < >= <= <> != [NOT] LIKE, [NOT] BETWEEN, [NOT] REGEXP, IS [NOT] NULL, IN, ANY, ALL, SOME, SOUNDS LIKE etc...
 * see: https://dev.mysql.com/doc/refman/8.0/en/expressions.html
 * if() is also a class that exists under expression even though it returns a boolean.
 * DateExpression, NumberExpression, StringExpression, BooleanExpression, PartialColumn will extend this class.
 * .eq() evaluates to a BooleanExpression. Example: select 1 = 1; //returns 1
 */
@Immutable
public interface Expression extends SelectExpression {

  default AliasedExpression as(String name) {
    return AliasedExpression.make(name, this);
  }

  BooleanExpression isGreaterThan(Expression i);

  BooleanExpression isLessThan(Expression i);

  BooleanExpression eq(Expression item);

  BooleanExpression eq(String value);
}
