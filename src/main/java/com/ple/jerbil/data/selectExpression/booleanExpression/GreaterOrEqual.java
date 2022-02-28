package com.ple.jerbil.data.selectExpression.booleanExpression;

import com.ple.jerbil.data.GenericInterfaces.Immutable;
import com.ple.jerbil.data.selectExpression.SelectExpression;

@Immutable
public class GreaterOrEqual implements BooleanExpression {

  public final SelectExpression e1;
  public final SelectExpression e2;

  public GreaterOrEqual(SelectExpression e1, SelectExpression e2) {
    this.e1 = e1;
    this.e2 = e2;
  }

  public static BooleanExpression make(SelectExpression e1, SelectExpression e2) {
    return new GreaterOrEqual(e1, e2);
  }
}
