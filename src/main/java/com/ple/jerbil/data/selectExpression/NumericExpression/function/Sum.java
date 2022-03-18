package com.ple.jerbil.data.selectExpression.NumericExpression.function;

import com.ple.jerbil.data.selectExpression.AliasedExpression;
import com.ple.jerbil.data.selectExpression.NumericExpression.NumericColumn;
import com.ple.jerbil.data.selectExpression.NumericExpression.NumericExpression;

import java.util.Objects;

public class Sum implements NumericExpression {
  public final NumericColumn numericColumn;

  public Sum(NumericColumn numericColumn) {
    this.numericColumn = numericColumn;
  }

  public static Sum make(NumericColumn i) {
    return new Sum(i);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Sum)) return false;
    Sum sum = (Sum) o;
    return numericColumn.equals(sum.numericColumn);
  }

  @Override
  public int hashCode() {
    return Objects.hash(numericColumn);
  }

  @Override
  public String toString() {
    return "Sum{" +
        "numericColumn=" + numericColumn +
        '}';
  }

  public AliasedExpression as(String alias) {
    return AliasedExpression.make(alias, this);
  }
}
