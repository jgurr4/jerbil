package com.ple.jerbil.sql.fromExpression;

import com.ple.jerbil.sql.expression.BooleanExpression;
import com.ple.jerbil.sql.query.QueryWithFrom;

public class FromExpression extends QueryWithFrom {

  protected FromExpression(FromExpression fromExpression, BooleanExpression condition) {
    super(fromExpression, condition);
  }

  protected FromExpression getFromExpression() {
    return this;
  }

}
