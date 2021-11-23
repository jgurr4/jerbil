package com.ple.jerbil.data.selectExpression.booleanExpression;

import com.ple.jerbil.data.Immutable;
import com.ple.util.IArrayList;
import com.ple.util.IList;

/**
 * Adds ability for Either-Or conditionals in 'where' + 'and' methods.
 */
@Immutable
public class Or extends BooleanExpression {

  public final IList<BooleanExpression> conditions;

  protected Or(IList<BooleanExpression> conditions) {
    this.conditions = conditions;
  }

  public static Or or(BooleanExpression... conditions) {
    return new Or(IArrayList.make(conditions));
  }

}
