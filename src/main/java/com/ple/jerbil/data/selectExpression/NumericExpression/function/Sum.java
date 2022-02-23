package com.ple.jerbil.data.selectExpression.NumericExpression.function;

import com.ple.jerbil.data.selectExpression.AliasedExpression;
import com.ple.jerbil.data.selectExpression.NumericExpression.NumericColumn;
import com.ple.jerbil.data.selectExpression.NumericExpression.NumericExpression;

public class Sum implements NumericExpression {
  public final NumericColumn numericColumn;

  public Sum(NumericColumn numericColumn) {
    this.numericColumn = numericColumn;
  }

  public static Sum make(NumericColumn i) {
    return new Sum(i);
  }

  public AliasedExpression as(String alias) {
    return AliasedExpression.make(alias, this);
  }
}
