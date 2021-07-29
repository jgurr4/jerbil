package com.ple.jerbil.sql.expression;

import com.ple.jerbil.sql.Immutable;

@Immutable
public class Expression extends SelectExpression {

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
