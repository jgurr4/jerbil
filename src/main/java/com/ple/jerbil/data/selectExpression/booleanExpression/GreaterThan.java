package com.ple.jerbil.data.selectExpression.booleanExpression;

import com.ple.jerbil.data.Immutable;
import com.ple.jerbil.data.selectExpression.*;
import com.ple.jerbil.data.selectExpression.AliasedExpression;

import java.util.Objects;

@Immutable
public class GreaterThan<T extends SelectExpression> implements BooleanExpression<T> {
  public final T e1;
  public final T e2;

  protected GreaterThan(T e1, T e2) {
    this.e1 = e1;
    this.e2 = e2;
  }

  public static <T extends SelectExpression> GreaterThan make(T e1, T e2) {
    return new GreaterThan(e1, e2);
  }


/*
  public BooleanExpression isGreaterThan(Expression i) {
    return null;
  }

  public BooleanExpression isLessThan(Expression i) {
    return null;
  }

  public BooleanExpression eq(Expression item) {
    return null;
  }
*/

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof GreaterThan)) return false;
    GreaterThan that = (GreaterThan) o;
    return e1.equals(that.e1) && e2.equals(that.e2);
  }

  @Override
  public int hashCode() {
    return Objects.hash(e1, e2);
  }

  @Override
  public String toString() {
    return "GreaterThan{" +
      "e1=" + e1 +
      ", e2=" + e2 +
      '}';
  }

}
