package com.ple.jerbil.sql.expression;

import com.ple.jerbil.sql.Immutable;
import com.ple.jerbil.sql.query.CompleteQuery;

@Immutable
public class SelectExpression {

  public final static SelectExpression selectAll = SelectAllExpression.make();

  public CompleteQuery select() {
    return CompleteQuery.make();
  }

}
