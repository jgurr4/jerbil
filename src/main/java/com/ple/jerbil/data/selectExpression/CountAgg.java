package com.ple.jerbil.data.selectExpression;

public class CountAgg implements UnaliasedExpression {

  public static CountAgg make() {
    return new CountAgg();
  }

  public AliasedExpression as(String alias) {
    return AliasedExpression.make(alias, this);
  }

}
