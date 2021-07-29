package com.ple.jerbil.sql.query;

import com.ple.jerbil.sql.Table;
import com.ple.jerbil.sql.expression.SelectExpression;

public class SelectQuery extends CompleteQuery {
  private final SelectExpression[] expressions;
  private final Table table;
  private final QueryType type;

  private SelectQuery(SelectExpression[] expressions, Table table, QueryType type) {
    this.expressions = expressions;
    this.table = table;
    this.type = type;
  }

  public static SelectQuery make(SelectExpression[] expressions, Table table, QueryType type) {
    return new SelectQuery(expressions, table, type);
  }

  public SelectQuery select(SelectExpression... expressions) {
    return null;
  }

}
