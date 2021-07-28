package com.ple.jerbil.sql.query;

import com.ple.jerbil.sql.Table;
import com.ple.jerbil.sql.expression.BooleanExpression;
import com.ple.jerbil.sql.expression.Expression;

public class PartialQuery extends Query { // A partial query could be 'from table where condition', or it could be 'where condition'

  private final BooleanExpression condition;
  private final Table table;

  private PartialQuery(Table table, BooleanExpression condition) {
    this.table = table;
    this.condition = condition;
  }

  public static PartialQuery make(Table table, BooleanExpression condition) {
    return new PartialQuery(table, condition);
  }

  public PartialQuery where(BooleanExpression condition) {
    return PartialQuery.make(table, condition);
  }

  public CompleteQuery select(Expression... expressions) {  // This is used to turn a partialQuery into a CompleteQuery.
    return CompleteQuery.make(QueryType.select, this.table, expressions);
  }

}
