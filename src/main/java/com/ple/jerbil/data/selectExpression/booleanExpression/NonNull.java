package com.ple.jerbil.data.selectExpression.booleanExpression;

import com.ple.jerbil.data.GenericInterfaces.Immutable;
import com.ple.jerbil.data.selectExpression.SelectExpression;

@Immutable
public class NonNull implements BooleanExpression<SelectExpression> {
  public final SelectExpression expression;

  public NonNull(SelectExpression expression) {
    this.expression = expression;
  }

  public static BooleanExpression make(SelectExpression expression) {
    return new NonNull(expression);
  }
}
