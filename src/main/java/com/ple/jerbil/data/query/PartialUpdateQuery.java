package com.ple.jerbil.data.query;

import com.ple.jerbil.data.Order;
import com.ple.jerbil.data.selectExpression.Column;
import com.ple.jerbil.data.selectExpression.Expression;
import com.ple.jerbil.data.selectExpression.SelectExpression;
import com.ple.jerbil.data.selectExpression.booleanExpression.BooleanExpression;
import com.ple.util.IList;
import com.ple.util.IMap;
import org.jetbrains.annotations.Nullable;

public class PartialUpdateQuery extends PartialQueryWithValues {

  protected PartialUpdateQuery(@Nullable BooleanExpression where, @Nullable FromExpression fromExpression,
                               @Nullable QueryType queryType, @Nullable IList<SelectExpression> select,
                               @Nullable IList<SelectExpression> groupBy, @Nullable IMap<SelectExpression, Order> orderBy,
                               @Nullable BooleanExpression having, @Nullable Limit limit,
                               @Nullable IList<IMap<Column, Expression>> set, @Nullable QueryFlags queryFlags) {
    super(where, fromExpression, queryType, select, groupBy, orderBy, having, limit, set, queryFlags);
  }
}
