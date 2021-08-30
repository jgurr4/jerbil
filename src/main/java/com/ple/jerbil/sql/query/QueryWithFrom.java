package com.ple.jerbil.sql.query;

import com.ple.jerbil.sql.Immutable;
import com.ple.jerbil.sql.fromExpression.Table;
import com.ple.jerbil.sql.expression.BooleanExpression;
import com.ple.jerbil.sql.fromExpression.FromExpression;
import com.ple.jerbil.sql.expression.SelectExpression;
import com.ple.util.IArrayList;
import org.jetbrains.annotations.Nullable;

import java.util.List;

@Immutable
public class QueryWithFrom extends PartialQuery {

  protected QueryWithFrom(@Nullable Table table) {
    super(table);
  }

  // The use of QueryWithFrom is it is used to show that a insert, update, and delete query is complete. Select is complete without tables. But the others require a table.
  public static QueryWithFrom make(FromExpression fromExpression) {
    return new QueryWithFrom(fromExpression);
  }

  public SelectQuery selectAll() {
    return SelectQuery.make(IArrayList.make(SelectExpression.selectAll));
  }

  public QueryWithFrom where(BooleanExpression condition) {
    return new QueryWithFrom(this.fromExpression, condition);
  }

}
