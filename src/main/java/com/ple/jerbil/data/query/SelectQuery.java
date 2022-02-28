package com.ple.jerbil.data.query;

import com.ple.jerbil.data.GenericInterfaces.Immutable;
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
              @Nullable Limit limit, @Nullable IList<IMap<Column, Expression>> set, @Nullable QueryFlags queryFlags,
              @Nullable Union union) {
    super(where, fromExpression, queryType, select, groupBy, orderBy, having, limit, set, queryFlags, union);
  }

  public static SelectQuery make(IList<SelectExpression> selectExpressions) {
    return new SelectQuery(null, null, QueryType.select, selectExpressions, null, null,
        null, null, null, QueryFlags.make(), null);
  }

  public static SelectQuery make(TableContainer tableContainer, IArrayList<SelectExpression> selectExpressions) {
    return new SelectQuery(null, tableContainer, QueryType.select, selectExpressions, null, null,
        null, null, null, QueryFlags.make(), null);
  }

  public static SelectQuery make(BooleanExpression where, FromExpression fromExpression, QueryType queryType,
                                 IList<SelectExpression> selectExpressions, IList<SelectExpression> groupBy,
                                 IMap<SelectExpression, Order> orderBy, BooleanExpression having, Limit limit,
                                 IList<IMap<Column, Expression>> set, QueryFlags queryFlags, Union union) {
    return new SelectQuery(where, fromExpression, queryType, selectExpressions, groupBy, orderBy, having, limit, set,
        queryFlags, union);
  }

  public static SelectQuery make(TableContainer tableContainer, IList<SelectExpression> selectExpressions,
                                 QueryFlags queryFlags) {
    return new SelectQuery(null, tableContainer, QueryType.select, selectExpressions, null, null,
        null, null, null, queryFlags, null);
  }

  public SelectQuery groupBy(SelectExpression... groupBy) {
    return new SelectQuery(where, fromExpression, queryType, select, IArrayList.make(groupBy), orderBy, having, limit,
        set, queryFlags, null);
  }

  public SelectQuery where(BooleanExpression<UnaliasedExpression> condition) {
    return SelectQuery.make(condition, fromExpression, queryType, select, groupBy, orderBy,
        having, limit, set, queryFlags, union);
  }

  //TODO: Add a field to Query that includes unionSelect. Then make an class called UnionSelect which contains a selectQuery as well as a non-null parameter called "UnionType" which will be all or distinct based on whether users specified unionAll or union.
  public CompleteQuery union(SelectQuery selectQuery) {
    return new SelectQuery(where, fromExpression, queryType, select, groupBy, orderBy, having, limit, set, queryFlags,
        Union.make(selectQuery, UnionType.distinct));
  }

  public CompleteQuery unionAll(SelectQuery selectQuery) {
    return new SelectQuery(where, fromExpression, queryType, select, groupBy, orderBy, having, limit, set, queryFlags,
        Union.make(selectQuery, UnionType.all));
  }

  public SelectQuery orderBy(SelectExpression expression, Order order) {
    return new SelectQuery(where, fromExpression, QueryType.select, select, groupBy, IArrayMap.make(expression, order),
        having, limit, set,
        queryFlags, union);
  }

  public SelectQuery orderBy(IMap<SelectExpression, Order> orderBy) {
    return new SelectQuery(where, fromExpression, QueryType.select, select, groupBy, orderBy, having, limit, set,
        queryFlags, union);
  }

  public SelectQuery orderBy(Order order, SelectExpression... selectExpressions) {
    IArrayMap<SelectExpression, Order> orderBy = IArrayMap.empty;
    for (SelectExpression selectExpression : selectExpressions) {
      orderBy = orderBy.put(selectExpression, order);
    }
    return new SelectQuery(where, fromExpression, QueryType.select, select, groupBy, orderBy, having, limit, set,
        queryFlags, union);
  }

  public SelectQuery orderBy(SelectExpression... selectExpressions) {
    IArrayMap<SelectExpression, Order> orderBy = IArrayMap.empty;
    for (SelectExpression selectExpression : selectExpressions) {
      orderBy = orderBy.put(selectExpression, Order.ascending);
    }
    return new SelectQuery(where, fromExpression, QueryType.select, select, groupBy, orderBy, having, limit, set,
        queryFlags, union);
  }

  public SelectQuery having(BooleanExpression having) {
    return new SelectQuery(where, fromExpression, queryType, select, groupBy, orderBy, having, limit,
        set, queryFlags, union);
  }

  public CompleteQuery explain() {
    return SelectQuery.make(where, fromExpression, queryType, select, groupBy, orderBy, having, limit, set,
        queryFlags.explain(), union);
  }

  public CompleteQuery analyze() {
    return SelectQuery.make(where, fromExpression, queryType, select, groupBy, orderBy, having, limit, set,
        queryFlags.analyze(), union);
  }
}
