package com.ple.jerbil.sql.query;

import com.ple.jerbil.sql.Table;
import com.ple.jerbil.sql.expression.BooleanExpression;
import com.ple.jerbil.sql.expression.Expression;
import com.ple.jerbil.sql.expression.SelectExpression;

public class PartialSelectQuery extends PartialQuery {
  private final SelectExpression condition;

  private PartialSelectQuery(SelectExpression condition) {
    this.condition = condition;
  }

  public static PartialSelectQuery make(SelectExpression condition) {
    return new PartialSelectQuery(condition);
  }

  public PartialSelectQuery where(BooleanExpression condition) {
    return PartialSelectQuery.make(condition);
  }

//  public CompleteQuery select(Expression... expressions) {  // This is used to turn a partialQuery into a CompleteQuery.
//    return CompleteQuery.make(QueryType.select, this.table, expressions);
//  }
}
