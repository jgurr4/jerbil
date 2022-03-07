package com.ple.jerbil.data.selectExpression.booleanExpression;

import com.ple.util.Immutable;
import com.ple.jerbil.data.selectExpression.Expression;
import com.ple.util.IArrayList;
import com.ple.util.IList;

import java.util.Objects;

/**
 * Adds ability for list of conditions for use with 'where' method.
 */
@Immutable
public class And implements BooleanExpression {

  public final IList<BooleanExpression> conditions;

  protected And(IList<BooleanExpression> conditions) {
    this.conditions = conditions;
  }

  public static And and(BooleanExpression... conditions) {
    return new And(IArrayList.make(conditions));
  }

  public static And make(IList<BooleanExpression> conditions) {
    return new And(conditions);
  }

  public BooleanExpression isGreaterThan(Expression i) {
    return null;
  }

  public BooleanExpression isLessThan(Expression i) {
    return null;
  }

  public BooleanExpression eq(Expression item) {
    return null;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof And)) return false;
    And and = (And) o;
    return conditions.equals(and.conditions);
  }

  @Override
  public int hashCode() {
    return Objects.hash(conditions);
  }

  @Override
  public String toString() {
    return "And{" +
      "conditions=" + conditions +
      '}';
  }

}
