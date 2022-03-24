package com.ple.jerbil.data.selectExpression;

import com.ple.jerbil.data.selectExpression.NumericExpression.NumericExpression;
import com.ple.jerbil.data.selectExpression.booleanExpression.BooleanExpression;
import com.ple.util.Immutable;

@Immutable
public class LiteralNull implements Literal, NumericExpression, StringExpression, DateExpression, BooleanExpression {
  public static LiteralNull make() {
    return new LiteralNull();
  }
}
