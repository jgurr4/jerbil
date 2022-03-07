package com.ple.jerbil.data.selectExpression.booleanExpression;

import com.ple.util.Immutable;
import com.ple.jerbil.data.selectExpression.NumericExpression.LiteralNumber;
import com.ple.jerbil.data.selectExpression.NumericExpression.NumericExpression;

@Immutable
public class NotBetween implements BooleanExpression<NumericExpression> {
  public final NumericExpression n1;
  public final LiteralNumber n2;
  public final LiteralNumber n3;

  public NotBetween(NumericExpression n1, LiteralNumber n2, LiteralNumber n3) {
    this.n1 = n1;
    this.n2 = n2;
    this.n3 = n3;
  }

  public static BooleanExpression make(NumericExpression n1, LiteralNumber n2, LiteralNumber n3) {
    return new NotBetween(n1, n2, n3);
  }
}
