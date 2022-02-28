package com.ple.jerbil.data.selectExpression.booleanExpression;

import com.ple.jerbil.data.GenericInterfaces.Immutable;
import com.ple.jerbil.data.selectExpression.SelectExpression;

@Immutable
public class False implements BooleanExpression<SelectExpression> {
  public final BooleanExpression<SelectExpression> expression;

  public False(BooleanExpression<SelectExpression> expression) {
    this.expression = expression;
  }

  public static BooleanExpression<SelectExpression> make(BooleanExpression<SelectExpression> expression) {
    return new False(expression);
  }
}
