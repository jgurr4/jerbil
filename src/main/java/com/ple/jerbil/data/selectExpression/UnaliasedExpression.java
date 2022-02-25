package com.ple.jerbil.data.selectExpression;

import com.ple.jerbil.data.selectExpression.booleanExpression.BooleanExpression;
import com.ple.jerbil.data.selectExpression.booleanExpression.NonNull;
import com.ple.jerbil.data.selectExpression.booleanExpression.Null;

public interface UnaliasedExpression extends SelectExpression{
  default BooleanExpression<UnaliasedExpression> isNull() {
    return Null.make(this);
  }

  default BooleanExpression<UnaliasedExpression> isNotNull() {
    return NonNull.make(this);
  }
}
