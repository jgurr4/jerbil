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
public class SelectQuery extends CompleteQuery {

  SelectQuery(@Nullable BooleanExpression where, @Nullable FromExpression fromExpression, @Nullable QueryType queryType,
              @Nullable IList<SelectExpression> select, @Nullable IList<SelectExpression> groupBy,
              @Nullable IMap<SelectExpression, Order> orderBy, @Nullable BooleanExpression having,
              @Nullable Limit limit, @Nullable IList<IMap<Column, Expression>> set, @Nullable QueryFlags queryFlags) {
    super(where, fromExpression, queryType, select, groupBy, orderBy, having, limit, set, queryFlags);
  }

  public static SelectQuery make(IList<SelectExpression> selectExpressions) {
    return new SelectQuery(null, null, QueryType.select, selectExpressions, null, null, null, null, null, null);
  }

  public static SelectQuery make(TableContainer tableContainer, IArrayList<SelectExpression> selectExpressions) {
    return new SelectQuery(null, tableContainer, QueryType.select, selectExpressions, null, null, null, null, null,
        null);
  }

  public static SelectQuery make(BooleanExpression where, FromExpression fromExpression, QueryType queryType,
                                 IList<SelectExpression> selectExpressions, IList<SelectExpression> groupBy,
                                 IMap<SelectExpression, Order> orderBy, BooleanExpression having, Limit limit,
                                 IList<IMap<Column, Expression>> set, QueryFlags queryFlags) {
    return new SelectQuery(where, fromExpression, queryType, selectExpressions, groupBy, orderBy, having, limit, set,
        queryFlags);
  }

  public static SelectQuery make(TableContainer tableContainer, IList<SelectExpression> selectExpressions,
                                 QueryFlags queryFlags) {
    return new SelectQuery(null, tableContainer, QueryType.select, selectExpressions, null, null,
        null, null, null, queryFlags);
  }

  public SelectQuery groupBy(SelectExpression... groupBy) {
    return new SelectQuery(where, fromExpression, queryType, select, IArrayList.make(groupBy), orderBy, having, limit,
        set, queryFlags);
  }

  //TODO: Add a field to Query that includes unionSelect. Then make an class called UnionSelect which contains a selectQuery as well as a non-null parameter called "UnionType" which will be all or distinct based on whether users specified unionAll or union.
  public CompleteQuery union(SelectQuery selectQuery) {
//    return new SelectQuery(where, fromExpression, queryType, select, groupBy, orderBy, having, limit, set, queryFlags, selectQuery);
    return null;
  }

  public CompleteQuery unionAll(SelectQuery select) {
    return null;
  }

  public CompleteQuery whereMatch(StringColumn column, LiteralString valueToMatch) {
    return null;
  }

  public SelectQuery orderBy(SelectExpression expression, Order order) {
    return new SelectQuery(where, fromExpression, QueryType.select, select, groupBy, IArrayMap.make(expression, order),
        having, limit, set,
        queryFlags);
  }

  public SelectQuery orderBy(IMap<SelectExpression, Order> orderBy) {
    return new SelectQuery(where, fromExpression, QueryType.select, select, groupBy, orderBy, having, limit, set,
        queryFlags);
  }

  public SelectQuery orderBy(Order order, SelectExpression... selectExpressions) {
    IArrayMap<SelectExpression, Order> orderBy = IArrayMap.empty;
    for (SelectExpression selectExpression : selectExpressions) {
      orderBy = orderBy.put(selectExpression, order);
    }
    return new SelectQuery(where, fromExpression, QueryType.select, select, groupBy, orderBy, having, limit, set,
        queryFlags);
  }

  public SelectQuery orderBy(SelectExpression... selectExpressions) {
    IArrayMap<SelectExpression, Order> orderBy = IArrayMap.empty;
    for (SelectExpression selectExpression : selectExpressions) {
      orderBy = orderBy.put(selectExpression, Order.ascending);
    }
    return new SelectQuery(where, fromExpression, QueryType.select, select, groupBy, orderBy, having, limit, set,
        queryFlags);
  }

  public SelectQuery having(BooleanExpression having) {
    return new SelectQuery(where, fromExpression, queryType, select, groupBy, orderBy, having, limit,
        set, queryFlags);
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
