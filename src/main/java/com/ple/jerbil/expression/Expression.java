package com.ple.jerbil.expression;

import com.ple.jerbil.Immutable;

@Immutable
public class Expression extends SelectExpression {

  public AliasedExpression as(String name) {
    // Return Aliased Field.

    return null;
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
    return Equals.from(this, Literal.from(item));
  }

}
