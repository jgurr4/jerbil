package com.ple.jerbil.sql.expression;

public class AliasedExpression extends SelectExpression {
  public final String name;

  protected AliasedExpression(String name) {
    this.name = name;
  }

  public static AliasedExpression make(String name) {
    return new AliasedExpression(name);
  }

}
