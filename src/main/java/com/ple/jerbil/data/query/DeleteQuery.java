package com.ple.jerbil.data.query;

import com.ple.jerbil.data.Immutable;
import com.ple.jerbil.data.Order;
import com.ple.jerbil.data.selectExpression.Column;
import com.ple.jerbil.data.selectExpression.Expression;
import com.ple.jerbil.data.selectExpression.SelectExpression;
import com.ple.jerbil.data.selectExpression.UnaliasedExpression;
import com.ple.jerbil.data.selectExpression.booleanExpression.BooleanExpression;
import com.ple.util.IList;
import com.ple.util.IMap;
import org.jetbrains.annotations.Nullable;

@Immutable
public class DeleteQuery extends CompleteQuery {

  protected DeleteQuery(@Nullable BooleanExpression where, @Nullable FromExpression fromExpression,
                        @Nullable QueryType queryType, @Nullable IList<SelectExpression> select,
                        @Nullable IList<SelectExpression> groupBy, @Nullable IMap<SelectExpression, Order> orderBy,
                        @Nullable BooleanExpression having, @Nullable Limit limit,
                        @Nullable IList<IMap<Column, Expression>> set, @Nullable QueryFlags queryFlags,
                        @Nullable Union union) {
    super(where, fromExpression, queryType, select, groupBy, orderBy, having, limit, set, queryFlags, union);
  }

  public static DeleteQuery make(BooleanExpression where, FromExpression fromExpression, QueryType queryType,
                                 IList<SelectExpression> select, IList<SelectExpression> groupBy,
                                 IMap<SelectExpression, Order> orderBy, BooleanExpression having, Limit limit,
                                 IList<IMap<Column, Expression>> set, QueryFlags queryFlags, Union union) {
    return new DeleteQuery(where, fromExpression, queryType, select, groupBy, orderBy, having, limit, set, queryFlags, union);
  }

  public DeleteQuery where(BooleanExpression<UnaliasedExpression> condition) {
    return DeleteQuery.make(condition, fromExpression, queryType, select, groupBy, orderBy,
        having, limit, set, queryFlags, union);
  }

/*
  public CompleteQuery limit(int offset, int limit) {
    return null;
  }

  public CompleteQuery limit(int limit) {
    return null;
  }

*/
}
