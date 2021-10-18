package com.ple.jerbil.sql.query;

import com.ple.jerbil.sql.Immutable;
import com.ple.jerbil.sql.fromExpression.FromExpression;
import com.ple.jerbil.sql.selectExpression.Column;
import com.ple.jerbil.sql.fromExpression.Table;
import com.ple.jerbil.sql.selectExpression.booleanExpression.BooleanExpression;
import com.ple.jerbil.sql.selectExpression.SelectExpression;
import com.ple.util.IArrayList;
import com.ple.util.IList;


@Immutable
public class QueryWithFrom extends PartialQuery {

  protected QueryWithFrom(FromExpression fromExpression, IList<BooleanExpression> where) {
    super(fromExpression);
  }

  public static QueryWithFrom make(Table table) {
    return new QueryWithFrom(table, null);
  }

  public SelectQuery selectAll() {
    return SelectQuery.make(IArrayList.make(SelectExpression.selectAll));
  }

  public QueryWithFrom where(BooleanExpression where) {
    return new QueryWithFrom(fromExpression, IArrayList.make(where));
  }

}
