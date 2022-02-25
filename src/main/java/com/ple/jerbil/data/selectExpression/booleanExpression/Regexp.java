package com.ple.jerbil.data.selectExpression.booleanExpression;

import com.ple.jerbil.data.Immutable;
import com.ple.jerbil.data.selectExpression.LiteralString;
import com.ple.jerbil.data.selectExpression.StringExpression;

@Immutable
public class Regexp implements BooleanExpression<StringExpression>{
  public final StringExpression e1;
  public final LiteralString e2;

  public Regexp(StringExpression e1, LiteralString e2) {
    this.e1 = e1;
    this.e2 = e2;
  }

  public static BooleanExpression<StringExpression> make(StringExpression e1, LiteralString e2) {
    return new Regexp(e1, e2);
  }
}
