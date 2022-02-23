package com.ple.jerbil.data.query;

import com.ple.jerbil.data.Immutable;
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
 * A partial Query that includes a tablename, but has not specified a crud operation/QueryType yet.
 */
@Immutable
public class QueryWithFrom extends PartialQuery {


  protected QueryWithFrom(@Nullable BooleanExpression where, @Nullable FromExpression fromExpression,
                          @Nullable QueryType queryType, @Nullable IList<SelectExpression> select,
                          @Nullable IList<SelectExpression> groupBy, @Nullable IMap<SelectExpression, Order> orderBy,
                          @Nullable IList<BooleanExpression> having, @Nullable Limit limit,
                          @Nullable IList<IMap<Column, Expression>> set, @Nullable InsertFlags insertFlags) {
    super(where, fromExpression, queryType, select, groupBy, orderBy, having, limit, set, insertFlags);
  }

  public static QueryWithFrom make(FromExpression fromExpression) {
    return new QueryWithFrom(null, fromExpression, null, null, null, null, null,
        null, null, null);
  }

  public SelectQuery selectAll() {
    return SelectQuery.make(where, fromExpression, QueryType.select, IArrayList.make(SelectExpression.selectAll),
        groupBy, orderBy, having, limit, set, insertFlags);
  }

  public QueryWithFrom where(BooleanExpression where) {
    return new QueryWithFrom(where, fromExpression, null, null, null, null, null,
        null, null, insertFlags);
  }

}
