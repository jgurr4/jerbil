package com.ple.jerbil.data.selectExpression.NumericExpression;


import com.ple.jerbil.data.Immutable;
import com.ple.jerbil.data.selectExpression.Expression;

@Immutable
public class ArithmeticExpression extends NumericExpression {

  public final Expression e1;
  public final Expression e2;
  public final Operator type;

  protected ArithmeticExpression(Expression e1, Expression e2, Operator type) {
    this.e1 = e1;
    this.e2 = e2;
    this.type = type;
  }

  public static ArithmeticExpression make(Expression e1, Expression e2, Operator type) {
    return new ArithmeticExpression(e1, e2, type);
  }

}
