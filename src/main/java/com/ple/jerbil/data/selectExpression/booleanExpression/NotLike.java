package com.ple.jerbil.data.selectExpression.booleanExpression;


import com.ple.util.Immutable;
import com.ple.jerbil.data.selectExpression.LiteralString;
import com.ple.jerbil.data.selectExpression.StringExpression;

@Immutable
public class NotLike implements BooleanExpression<StringExpression> {
  public final StringExpression s1;
  public final LiteralString s2;

  public NotLike(StringExpression s1, LiteralString s2) {
    this.s1 = s1;
    this.s2 = s2;
  }

  public static BooleanExpression make(StringExpression s1, LiteralString s2) {
    return new NotLike(s1, s2);
  }
}
