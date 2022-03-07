package com.ple.jerbil.data.query;

import com.ple.util.Immutable;
import com.ple.jerbil.data.SortOrder;
import com.ple.jerbil.data.selectExpression.Column;
import com.ple.jerbil.data.selectExpression.Expression;
import com.ple.jerbil.data.selectExpression.SelectExpression;
import com.ple.jerbil.data.selectExpression.booleanExpression.BooleanExpression;
import com.ple.util.IList;
import com.ple.util.IMap;
import org.jetbrains.annotations.Nullable;

/**
 * For use with PartialUpdate, PartialInsert and PartialReplace queries, because they require .set method to list columns and the values to put in/replace.
 */
@Immutable
public class PartialQueryWithValues extends PartialQuery {

  protected PartialQueryWithValues(@Nullable BooleanExpression where, @Nullable FromExpression fromExpression,
                                   @Nullable QueryType queryType, @Nullable IList<SelectExpression> select,
                                   @Nullable IList<SelectExpression> groupBy, @Nullable IMap<SelectExpression, SortOrder> orderBy,
                                   @Nullable BooleanExpression having, @Nullable Limit limit,
                                   @Nullable IList<IMap<Column, Expression>> set, @Nullable QueryFlags queryFlags,
                                   @Nullable Union union) {
    super(where, fromExpression, queryType, select, groupBy, orderBy, having, limit, set, queryFlags, union);
  }

}
