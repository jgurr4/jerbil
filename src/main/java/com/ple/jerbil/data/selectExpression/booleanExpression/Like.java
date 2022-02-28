package com.ple.jerbil.data.selectExpression.booleanExpression;

import com.ple.jerbil.data.GenericInterfaces.Immutable;
import com.ple.jerbil.data.selectExpression.LiteralString;
import com.ple.jerbil.data.selectExpression.StringExpression;

@Immutable
public class Like implements BooleanExpression<StringExpression> {
  public final StringExpression s1;
  public final LiteralString s2;

  public Like(StringExpression s1, LiteralString s2) {
    this.s1 = s1;
    this.s2 = s2;
  }

  public static BooleanExpression make(StringExpression s1, LiteralString s2) {
    return new Like(s1, s2);
  }
}
