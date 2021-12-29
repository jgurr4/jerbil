package com.ple.jerbil.data.selectExpression.booleanExpression;

import com.ple.jerbil.data.Immutable;
import com.ple.jerbil.data.selectExpression.Expression;
import com.ple.util.IArrayList;
import com.ple.util.IList;

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

  @Override
  public BooleanExpression isGreaterThan(Expression i) {
    return null;
  }

  @Override
  public BooleanExpression isLessThan(Expression i) {
    return null;
  }

  @Override
  public BooleanExpression eq(Expression item) {
    return null;
  }

}
