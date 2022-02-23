package com.ple.jerbil.data.selectExpression;

import org.jetbrains.annotations.Nullable;

public class CountAgg implements SelectExpression {

  public static CountAgg make() {
    return new CountAgg();
  }

  public AliasedExpression as(String alias) {
    return AliasedExpression.make(alias, this);
  }

}
