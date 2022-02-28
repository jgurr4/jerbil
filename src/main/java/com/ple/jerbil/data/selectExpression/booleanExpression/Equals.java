package com.ple.jerbil.data.selectExpression.booleanExpression;

import com.ple.jerbil.data.GenericInterfaces.Immutable;
import com.ple.jerbil.data.selectExpression.SelectExpression;

import java.util.Objects;

/**
 * Every single operator for booleanExpression is a class. So =, >, <, like, regexp.
 * Literals are not allowed to mix to prevent things like this:
 * select where 5 = false;
 */
@Immutable
public class Equals<T extends SelectExpression> implements BooleanExpression<T> {

  public final T e1;
  public final T e2;

  protected Equals(T e1, T e2) {
    this.e1 = e1;
    this.e2 = e2;
  }

  public static <T extends SelectExpression> Equals make(T e1, T e2) {
   return new Equals(e1, e2);
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
    if (!(o instanceof Equals)) return false;
    Equals equals = (Equals) o;
    return e1.equals(equals.e1) && e2.equals(equals.e2);
  }

  @Override
  public int hashCode() {
    return Objects.hash(e1, e2);
  }

  @Override
  public String toString() {
    return "Equals{" +
      "e1=" + e1 +
      ", e2=" + e2 +
      '}';
  }

}
