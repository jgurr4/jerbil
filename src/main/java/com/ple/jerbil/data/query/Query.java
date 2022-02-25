package com.ple.jerbil.data.query;


import com.ple.jerbil.data.Immutable;
import com.ple.jerbil.data.Order;
import com.ple.jerbil.data.PotentialQuery;
import com.ple.jerbil.data.selectExpression.Column;
import com.ple.jerbil.data.selectExpression.Expression;
import com.ple.jerbil.data.selectExpression.SelectExpression;
import com.ple.jerbil.data.selectExpression.booleanExpression.BooleanExpression;
import com.ple.util.IArrayList;
import com.ple.util.IList;
import com.ple.util.IMap;
import org.jetbrains.annotations.Nullable;

@Immutable
public class Query extends PotentialQuery {

  @Nullable public final BooleanExpression where;
  @Nullable public final FromExpression fromExpression;
  @Nullable public final QueryType queryType;
  @Nullable public final IList<SelectExpression> select;
  @Nullable public final IList<SelectExpression> groupBy;
  @Nullable public final IMap<SelectExpression, Order> orderBy;
  @Nullable public final BooleanExpression having;
  @Nullable public final Limit limit;
  @Nullable public final IList<IMap<Column, Expression>> set;
  @Nullable public final QueryFlags queryFlags;

  protected Query(@Nullable BooleanExpression where, @Nullable FromExpression fromExpression,
                  @Nullable QueryType queryType, @Nullable IList<SelectExpression> select,
                  @Nullable IList<SelectExpression> groupBy, @Nullable IMap<SelectExpression, Order> orderBy,
                  @Nullable BooleanExpression having, @Nullable Limit limit,
                  @Nullable IList<IMap<Column, Expression>> set, @Nullable QueryFlags queryFlags) {
    this.where = where;
    this.fromExpression = fromExpression;
    this.queryType = queryType;
    this.select = select;
    this.groupBy = groupBy;
    this.orderBy = orderBy;
    this.having = having;
    this.limit = limit;
    this.set = set;
    this.queryFlags = queryFlags;
  }

  @Override
  public SelectQuery select(SelectExpression... selectExpressions) {
    return SelectQuery.make(where, fromExpression, QueryType.select, IArrayList.make(selectExpressions), groupBy,
        orderBy, having, limit, set, queryFlags);
  }

  public SelectQuery select() {
    return SelectQuery.make(where, fromExpression, QueryType.select, IArrayList.make(SelectExpression.selectAll), groupBy,
        orderBy, having, limit, set, queryFlags);
  }
}
