package com.ple.jerbil.data.selectExpression;

public class CountAgg implements SelectExpression {

  public static CountAgg make() {
    return new CountAgg();
  }

  public AliasedExpression as(String alias) {
    return null;
  }

}
