package com.ple.jerbil.data.query;

import com.ple.jerbil.data.Immutable;
import com.ple.jerbil.data.Order;
import com.ple.jerbil.data.selectExpression.*;
import com.ple.jerbil.data.selectExpression.booleanExpression.BooleanExpression;
import com.ple.util.IArrayList;
import com.ple.util.IArrayMap;
import com.ple.util.IList;
import com.ple.util.IMap;
import org.jetbrains.annotations.Nullable;

@Immutable
public class UpdateQuery extends CompleteQuery {

  protected UpdateQuery(@Nullable BooleanExpression where, @Nullable FromExpression fromExpression,
                        @Nullable QueryType queryType, @Nullable IList<SelectExpression> select,
                        @Nullable IList<SelectExpression> groupBy, @Nullable IMap<SelectExpression, Order> orderBy,
                        @Nullable BooleanExpression having, @Nullable Limit limit,
                        @Nullable IList<IMap<Column, Expression>> set, @Nullable QueryFlags queryFlags,
                        @Nullable Union union) {
    super(where, fromExpression, queryType, select, groupBy, orderBy, having, limit, set, queryFlags, union);
  }

  public static UpdateQuery make(IList<IMap<Column, Expression>> set, FromExpression fromExpression, QueryType queryType) {
    return new UpdateQuery(null, fromExpression, queryType, null, null, null,
        null, null, set, null, null);
  }

  public static UpdateQuery make(BooleanExpression where, FromExpression fromExpression, QueryType queryType,
                                 IList<SelectExpression> select, IList<SelectExpression> groupBy,
                                 IMap<SelectExpression, Order> orderBy, BooleanExpression having, Limit limit,
                                 IList<IMap<Column, Expression>> set, QueryFlags queryFlags, Union union) {
    return new UpdateQuery(where, fromExpression, queryType, select, groupBy, orderBy, having, limit, set, queryFlags,
        union);
  }

  public UpdateQuery where(BooleanExpression<UnaliasedExpression> condition) {
    return UpdateQuery.make(condition, fromExpression, queryType, select, groupBy, orderBy,
        having, limit, set, queryFlags, union);
  }

  public UpdateQuery set(Column column, Literal value) {
    if (set == null) {
      return UpdateQuery.make(IArrayList.make(IArrayMap.make(column, value)), fromExpression, queryType);
    }
    final IMap<Column, Expression> map = set.get(0).put(column, value);
    final IList<IMap<Column, Expression>> records = IArrayList.make(map);
    return UpdateQuery.make(records, fromExpression, queryType);
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
