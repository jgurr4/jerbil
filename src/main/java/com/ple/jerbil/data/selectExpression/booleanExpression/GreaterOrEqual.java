package com.ple.jerbil.data.selectExpression.booleanExpression;

import com.ple.jerbil.data.Immutable;
import com.ple.jerbil.data.selectExpression.NumericExpression.NumericExpression;

@Immutable
public class GreaterOrEqual implements BooleanExpression {

  public final NumericExpression e1;
  public final NumericExpression e2;

  public GreaterOrEqual(NumericExpression e1, NumericExpression e2) {
    this.e1 = e1;
    this.e2 = e2;
  }

  public static BooleanExpression make(NumericExpression e1, NumericExpression e2) {
    return new GreaterOrEqual(e1, e2);
  }
}
