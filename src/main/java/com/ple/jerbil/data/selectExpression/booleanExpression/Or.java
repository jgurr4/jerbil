package com.ple.jerbil.data.selectExpression.booleanExpression;

import com.ple.util.Immutable;
import com.ple.jerbil.data.selectExpression.Expression;
import com.ple.util.IArrayList;
import com.ple.util.IList;

import java.util.Objects;

/**
 * Adds ability for Either-Or conditionals in 'where' + 'and' methods.
 */
@Immutable
public class Or implements BooleanExpression {

  public final IList<BooleanExpression> conditions;

  protected Or(IList<BooleanExpression> conditions) {
    this.conditions = conditions;
  }

  public static Or or(BooleanExpression... conditions) {
    return new Or(IArrayList.make(conditions));
  }

  public static Or make(IList<BooleanExpression> conditions) {
    return new Or(conditions);
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
    if (!(o instanceof Or)) return false;
    Or or = (Or) o;
    return conditions.equals(or.conditions);
  }

  @Override
  public int hashCode() {
    return Objects.hash(conditions);
  }

  @Override
  public String toString() {
    return "Or{" +
      "conditions=" + conditions +
      '}';
  }

}
