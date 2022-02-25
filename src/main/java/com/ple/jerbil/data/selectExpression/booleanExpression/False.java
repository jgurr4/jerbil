package com.ple.jerbil.data.selectExpression.booleanExpression;

import com.ple.jerbil.data.Immutable;
import com.ple.jerbil.data.selectExpression.BooleanColumn;
import com.ple.jerbil.data.selectExpression.SelectExpression;
import com.ple.jerbil.data.selectExpression.UnaliasedExpression;

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
