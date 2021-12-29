package com.ple.jerbil.data.selectExpression.booleanExpression;

import com.ple.jerbil.data.Immutable;
import com.ple.jerbil.data.selectExpression.Expression;

/**
 * Every single operator for booleanExpression is a class. So =, >, <, like, regexp.
 * Literals are not allowed to mix to prevent things like this:
 * select where 5 = false;
 */
@Immutable
public class Equals implements BooleanExpression {

  public final Expression e1;
  public final Expression e2;

  protected Equals(Expression e1, Expression e2) {
    this.e1 = e1;
    this.e2 = e2;
  }

  public static Equals make(Expression e1, Expression e2) {
   return new Equals(e1, e2);
  }

  @Override
  public BooleanExpression isGreaterThan(Expression i) {
    return null;
  }

  @Override
  public BooleanExpression isLessThan(Expression i) {
    return null;
  }

  @Override
  public BooleanExpression eq(Expression item) {
    return null;
  }

}
