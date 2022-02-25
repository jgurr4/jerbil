package com.ple.jerbil.data.query;

import com.ple.jerbil.data.DelayedImmutable;
import com.ple.jerbil.data.Order;
import com.ple.jerbil.data.selectExpression.Column;
import com.ple.jerbil.data.selectExpression.Expression;
import com.ple.jerbil.data.selectExpression.Literal;
import com.ple.jerbil.data.selectExpression.SelectExpression;
import com.ple.jerbil.data.selectExpression.booleanExpression.BooleanExpression;
import com.ple.util.IArrayList;
import com.ple.util.IHashMap;
import com.ple.util.IList;
import com.ple.util.IMap;
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
                        @Nullable IList<IMap<Column, Expression>> set, @Nullable QueryFlags queryFlags) {
    super(where, fromExpression, queryType, select, groupBy, orderBy, having, limit, set, queryFlags);
  }

  public static InsertQuery make(Column column, Literal value, FromExpression fromExpression) {
    return new InsertQuery(null, fromExpression, QueryType.insert, null, null, null,
        null, null, IArrayList.make(IHashMap.make(column, value)), null);
  }

  public static InsertQuery make(Column column, Literal value, FromExpression fromExpression, boolean mayReplace) {
    return new InsertQuery(null, fromExpression, QueryType.insert, null, null, null,
        null, null, IArrayList.make(IHashMap.make(column, value)), null);
  }

  public static InsertQuery make(IList<IMap<Column, Expression>> set, FromExpression fromExpression,
                                 boolean mayReplace) {
    return new InsertQuery(null, fromExpression, QueryType.insert, null, null, null,
        null, null, set, null);
  }

  public static InsertQuery make(IList<IMap<Column, Expression>> set, FromExpression fromExpression) {
    return new InsertQuery(null, fromExpression, QueryType.insert, null, null, null,
        null, null, set, null);
  }

  public static InsertQuery make(BooleanExpression where, FromExpression fromExpression, QueryType queryType,
                                 IList<SelectExpression> select, IList<SelectExpression> groupBy,
                                 IMap<SelectExpression, Order> orderBy, BooleanExpression having, Limit limit,
                                 IList<IMap<Column, Expression>> set, QueryFlags queryFlags) {
    return new InsertQuery(where, fromExpression, queryType, select, groupBy, orderBy, having, limit, set, queryFlags);
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
