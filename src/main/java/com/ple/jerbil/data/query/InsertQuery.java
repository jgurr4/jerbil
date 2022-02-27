package com.ple.jerbil.data.query;

import com.ple.jerbil.data.DelayedImmutable;
import com.ple.jerbil.data.Order;
import com.ple.jerbil.data.selectExpression.*;
import com.ple.jerbil.data.selectExpression.booleanExpression.BooleanExpression;
import com.ple.util.*;
import org.jetbrains.annotations.Nullable;

/**
 * InsertQuery is a CompleteQuery that requires set, fromExpression and queryType fields to be not null.
 */
@DelayedImmutable
public class InsertQuery extends CompleteQuery {

  protected InsertQuery(@Nullable BooleanExpression where, @Nullable FromExpression fromExpression,
                        @Nullable QueryType queryType, @Nullable IList<SelectExpression> select,
                        @Nullable IList<SelectExpression> groupBy, @Nullable IMap<SelectExpression, Order> orderBy,
                        @Nullable BooleanExpression having, @Nullable Limit limit,
                        @Nullable IList<IMap<Column, Expression>> set, @Nullable QueryFlags queryFlags,
                        @Nullable Union union) {
    super(where, fromExpression, queryType, select, groupBy, orderBy, having, limit, set, queryFlags, union);
  }

  public static InsertQuery make(Column column, Literal value, FromExpression fromExpression) {
    return new InsertQuery(null, fromExpression, QueryType.insert, null, null, null,
        null, null, IArrayList.make(IHashMap.make(column, value)), QueryFlags.make(), null);
  }

  public static InsertQuery make(IList<IMap<Column, Expression>> set, FromExpression fromExpression,
                                 QueryFlags queryFlags) {
    return new InsertQuery(null, fromExpression, QueryType.insert, null, null, null,
        null, null, set, queryFlags, null);
  }

  public static InsertQuery make(IList<IMap<Column, Expression>> set, FromExpression fromExpression) {
    return new InsertQuery(null, fromExpression, QueryType.insert, null, null, null,
        null, null, set, QueryFlags.make(), null);
  }

  public static InsertQuery make(BooleanExpression where, FromExpression fromExpression, QueryType queryType,
                                 IList<SelectExpression> select, IList<SelectExpression> groupBy,
                                 IMap<SelectExpression, Order> orderBy, BooleanExpression having, Limit limit,
                                 IList<IMap<Column, Expression>> set, QueryFlags queryFlags, Union union) {
    return new InsertQuery(where, fromExpression, queryType, select, groupBy, orderBy, having, limit, set, queryFlags,
        union);
  }
/*

  public InsertQuery where(BooleanExpression<UnaliasedExpression> condition) {
    return InsertQuery.make(condition, fromExpression, queryType, select, groupBy, orderBy,
        having, limit, set, queryFlags, union);
  }
*/

  public InsertQuery set(Column column, Literal value) {
    if (set == null) {
      return new InsertQuery(where, fromExpression, queryType, select, groupBy, orderBy, having, limit,
          IArrayList.make(IArrayMap.make(column, value)), queryFlags, union);
    }
    final IMap<Column, Expression> map = set.get(0).put(column, value);
    final IList<IMap<Column, Expression>> records = IArrayList.make(map);
    return new InsertQuery(where, fromExpression, queryType, select, groupBy, orderBy, having, limit, records,
        queryFlags, union);
  }
}
