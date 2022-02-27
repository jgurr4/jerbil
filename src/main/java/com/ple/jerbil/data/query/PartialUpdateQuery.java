package com.ple.jerbil.data.query;

import com.ple.jerbil.data.Immutable;
import com.ple.jerbil.data.Order;
import com.ple.jerbil.data.selectExpression.Column;
import com.ple.jerbil.data.selectExpression.Expression;
import com.ple.jerbil.data.selectExpression.Literal;
import com.ple.jerbil.data.selectExpression.SelectExpression;
import com.ple.jerbil.data.selectExpression.booleanExpression.BooleanExpression;
import com.ple.util.*;
import org.jetbrains.annotations.Nullable;

@Immutable
public class PartialUpdateQuery extends PartialQueryWithValues {

  protected PartialUpdateQuery(@Nullable BooleanExpression where, @Nullable FromExpression fromExpression,
                               @Nullable QueryType queryType, @Nullable IList<SelectExpression> select,
                               @Nullable IList<SelectExpression> groupBy,
                               @Nullable IMap<SelectExpression, Order> orderBy,
                               @Nullable BooleanExpression having, @Nullable Limit limit,
                               @Nullable IList<IMap<Column, Expression>> set, @Nullable QueryFlags queryFlags,
                               @Nullable Union union) {
    super(where, fromExpression, queryType, select, groupBy, orderBy, having, limit, set, queryFlags, union);
  }

  public static PartialUpdateQuery make(FromExpression fromExpression) {
    return new PartialUpdateQuery(null, fromExpression, QueryType.update, null, null, null,
        null, null, null, QueryFlags.make(), null);
  }

  public UpdateQuery set(Column column, Literal value) {
    return UpdateQuery.make(IArrayList.make(IArrayMap.make(column, value)), fromExpression, queryType);
  }
}
