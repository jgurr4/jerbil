package com.ple.jerbil.data.selectExpression.booleanExpression;


import com.ple.util.Immutable;
import com.ple.jerbil.data.selectExpression.SelectExpression;

@Immutable
public class True implements BooleanExpression<SelectExpression> {
  public final BooleanExpression<SelectExpression> expression;

  public True(BooleanExpression<SelectExpression> expression) {
    this.expression = expression;
  }

  public static BooleanExpression<SelectExpression> make(BooleanExpression<SelectExpression> expression) {
    return new True(expression);
  }
}
