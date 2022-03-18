package com.ple.jerbil.data.selectExpression.NumericExpression;


import com.ple.util.Immutable;
import com.ple.jerbil.data.selectExpression.Expression;
import com.ple.jerbil.data.selectExpression.booleanExpression.BooleanExpression;
import com.ple.jerbil.data.selectExpression.booleanExpression.GreaterThan;

import java.util.Objects;

@Immutable
public class ArithmeticExpression implements NumericExpression {

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

  public GreaterThan isGreaterThan(Expression i) {
    return GreaterThan.make(this, i);
  }

  public BooleanExpression isLessThan(Expression i) {
    return null;
  }

  public BooleanExpression eq(Expression item) {
    return null;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof ArithmeticExpression)) return false;
    ArithmeticExpression that = (ArithmeticExpression) o;
    return e1.equals(that.e1) && e2.equals(that.e2) && type == that.type;
  }

  @Override
  public int hashCode() {
    return Objects.hash(e1, e2, type);
  }
}
