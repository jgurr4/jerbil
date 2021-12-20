package com.ple.jerbil.data.selectExpression;

public class CountAgg implements SelectExpression {

  public final String alias;

  protected CountAgg(String alias) {
    this.alias = alias;
  }

  public static CountAgg make() {
    return new CountAgg(null);
  }

  public AliasedExpression as(String alias) {
    return AliasedExpression.make(alias, this);
  }

}
