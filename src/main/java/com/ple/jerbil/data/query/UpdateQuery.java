package com.ple.jerbil.data.query;

import com.ple.jerbil.data.Immutable;
import com.ple.jerbil.data.Order;
import com.ple.jerbil.data.selectExpression.Column;
import com.ple.jerbil.data.selectExpression.Expression;
import com.ple.jerbil.data.selectExpression.SelectExpression;
import com.ple.jerbil.data.selectExpression.booleanExpression.BooleanExpression;
import com.ple.util.IList;
import com.ple.util.IMap;
import org.jetbrains.annotations.Nullable;

@Immutable
public class UpdateQuery extends CompleteQuery {


  protected UpdateQuery(@Nullable BooleanExpression where, @Nullable FromExpression fromExpression,
                        @Nullable QueryType queryType, @Nullable IList<SelectExpression> select,
                        @Nullable IList<SelectExpression> groupBy, @Nullable IMap<SelectExpression, Order> orderBy,
                        @Nullable IList<BooleanExpression> having, @Nullable Limit limit,
                        @Nullable IList<IMap<Column, Expression>> set, @Nullable InsertFlags insertFlags) {
    super(where, fromExpression, queryType, select, groupBy, orderBy, having, limit, set, insertFlags);
  }

  public static UpdateQuery make(IList<IMap<Column, Expression>> set, FromExpression fromExpression) {
    return new UpdateQuery(null, fromExpression, null, null, null, null,
        null, null, set, null);
  }

  public static UpdateQuery make(BooleanExpression where, FromExpression fromExpression, QueryType queryType,
                                   IList<SelectExpression> select, IList<SelectExpression> groupBy,
                                   IMap<SelectExpression, Order> orderBy, IList<BooleanExpression> having, Limit limit,
                                   IList<IMap<Column, Expression>> set, InsertFlags insertFlags) {
    return new UpdateQuery(where, fromExpression, queryType, select, groupBy, orderBy, having, limit, set, insertFlags);
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
