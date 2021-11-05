package com.ple.jerbil.data.selectExpression.booleanExpression;

import com.ple.jerbil.data.selectExpression.Expression;
import com.ple.jerbil.data.selectExpression.Literal;
import com.ple.jerbil.data.selectExpression.StringColumn;

/**
 * Every single operator for booleanExpression is a class. So =, >, <, like, regexp.
 * Literals are not allowed to mix to prevent things like this:
 * select where 5 = false;
 */
public class Equals extends BooleanExpression {

  public final Expression e1;
  public final Expression e2;

  protected Equals(Expression e1, Expression e2) {
    this.e1 = e1;
    this.e2 = e2;
  }

  public static Equals make(Expression e1, Expression e2) {
   return new Equals(e1, e2);
  }

}
