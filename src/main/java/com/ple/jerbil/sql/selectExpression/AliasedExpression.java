package com.ple.jerbil.sql.selectExpression;

import com.ple.jerbil.sql.Immutable;

@Immutable
public class AliasedExpression extends SelectExpression {
  public final String name;

  protected AliasedExpression(String name) {
    this.name = name;
  }

  public static AliasedExpression make(String name) {
    return new AliasedExpression(name);
  }

}
