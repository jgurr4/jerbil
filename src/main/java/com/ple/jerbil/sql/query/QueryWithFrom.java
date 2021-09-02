package com.ple.jerbil.sql.query;

import com.ple.jerbil.sql.Immutable;
import com.ple.jerbil.sql.selectExpression.Column;
import com.ple.jerbil.sql.fromExpression.Table;
import com.ple.jerbil.sql.selectExpression.booleanExpression.BooleanExpression;
import com.ple.jerbil.sql.selectExpression.SelectExpression;
import com.ple.util.IArrayList;


@Immutable
public class QueryWithFrom extends PartialQuery {

  public final BooleanExpression condition;

  protected QueryWithFrom(Table table, BooleanExpression condition) {
    super(table);
    this.condition = condition;
  }

  public static QueryWithFrom make(Table table) {
    return new QueryWithFrom(table, null);
  }

  public SelectQuery selectAll() {
    return SelectQuery.make(IArrayList.make(SelectExpression.selectAll));
  }

  public QueryWithFrom where(BooleanExpression condition) {
    return new QueryWithFrom(this.table, condition);
  }

  public CompleteQuery select(Column column) {
    return new CompleteQuery(this.table, column, null, SelectQuery.make(null));
  }

}
