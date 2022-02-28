package com.ple.jerbil.data.query;

import com.ple.jerbil.data.GenericInterfaces.Immutable;
import com.ple.jerbil.data.Order;
import com.ple.jerbil.data.selectExpression.Column;
import com.ple.jerbil.data.selectExpression.Expression;
import com.ple.jerbil.data.selectExpression.booleanExpression.BooleanExpression;
import com.ple.jerbil.data.selectExpression.SelectExpression;
import com.ple.util.IArrayList;
import com.ple.util.IList;
import com.ple.util.IMap;
import org.jetbrains.annotations.Nullable;


/**
 * A partial Query that includes a tablename, but has not specified a crud operation/QueryType yet. This is required to
 * allow users to build a query in any order they want.
 */
@Immutable
public class QueryWithFrom extends PartialQuery {


  protected QueryWithFrom(@Nullable BooleanExpression where, @Nullable FromExpression fromExpression,
                          @Nullable QueryType queryType, @Nullable IList<SelectExpression> select,
                          @Nullable IList<SelectExpression> groupBy, @Nullable IMap<SelectExpression, Order> orderBy,
                          @Nullable BooleanExpression having, @Nullable Limit limit,
                          @Nullable IList<IMap<Column, Expression>> set, @Nullable QueryFlags queryFlags,
                          @Nullable Union union) {
    super(where, fromExpression, queryType, select, groupBy, orderBy, having, limit, set, queryFlags, union);
  }

  public static QueryWithFrom make(FromExpression fromExpression) {
    return new QueryWithFrom(null, fromExpression, null, null, null, null, null,
        null, null, QueryFlags.make(), null);
  }

  public static QueryWithFrom make(TableContainer tableContainer, QueryFlags queryFlags) {
    return new QueryWithFrom(null, tableContainer, null, null, null, null, null,
        null, null, queryFlags, null);
  }

  public SelectQuery selectAll() {
    return SelectQuery.make(where, fromExpression, QueryType.select, IArrayList.make(SelectExpression.selectAll),
        groupBy, orderBy, having, limit, set, queryFlags, union);
  }

  public QueryWithFrom where(BooleanExpression where) {
    return new QueryWithFrom(where, fromExpression, null, null, null, null, null,
        null, null, queryFlags, null);
  }

}
