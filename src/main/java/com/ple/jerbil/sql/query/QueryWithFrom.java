package com.ple.jerbil.sql.query;

import com.ple.jerbil.sql.Immutable;
import com.ple.jerbil.sql.fromExpression.Table;
import com.ple.jerbil.sql.expression.BooleanExpression;
import com.ple.jerbil.sql.fromExpression.FromExpression;
import com.ple.jerbil.sql.expression.SelectExpression;
import com.ple.util.IArrayList;

@Immutable
public class QueryWithFrom extends Query {

  protected QueryWithFrom(FromExpression table) {
    super(table);
  }

  public static QueryWithFrom make(Table table, BooleanExpression condition) {
    return null;
  }

  public SelectQuery selectAll() {
    return SelectQuery.make(IArrayList.make(SelectExpression.selectAll));
  }

}
