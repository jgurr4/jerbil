package com.ple.jerbil.data.query;

import com.ple.jerbil.data.Immutable;
import com.ple.jerbil.data.selectExpression.Column;
import com.ple.jerbil.data.selectExpression.Expression;
import com.ple.jerbil.data.selectExpression.booleanExpression.BooleanExpression;
import com.ple.jerbil.data.selectExpression.SelectExpression;
import com.ple.util.IArrayList;
import com.ple.util.IList;
import com.ple.util.IMap;
import org.jetbrains.annotations.Nullable;


@Immutable
public class QueryWithFrom extends PartialQuery {


  protected QueryWithFrom(@Nullable IList<BooleanExpression> where, @Nullable FromExpression fromExpression, @Nullable QueryType queryType, @Nullable IList<SelectExpression> select, @Nullable IList<SelectExpression> groupBy, @Nullable IList<SelectExpression> orderBy, @Nullable IList<BooleanExpression> having, @Nullable Limit limit, @Nullable IMap<Column, Expression> set, @Nullable boolean mayInsert, @Nullable boolean mayReplace, @Nullable boolean triggerDeleteWhenReplacing, @Nullable boolean mayThrowOnDuplicate) {
    super(where, fromExpression, queryType, select, groupBy, orderBy, having, limit, set, mayInsert, mayReplace, triggerDeleteWhenReplacing, mayThrowOnDuplicate);
  }

  public static QueryWithFrom make(Table table) {
    return new QueryWithFrom(null, table, null, null, null, null, null, null, null, false, false, false, false);
  }

  public SelectQuery selectAll() {
    return SelectQuery.make(IArrayList.make(SelectExpression.selectAll));
  }

  public QueryWithFrom where(IList<BooleanExpression> where) {
    return new QueryWithFrom(where, null, null, null, null, null, null, null, null, false, false, false, false);
  }

}
