package com.ple.jerbil.data.query;

import com.ple.jerbil.data.Immutable;
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
 * For use with PartialUpdate, PartialInsert and PartialReplace queries, because they require .set method to list columns and the values to put in/replace.
 */
@Immutable
public class PartialQueryWithValues extends PartialQuery {

  protected PartialQueryWithValues(@Nullable BooleanExpression where, @Nullable FromExpression fromExpression,
                                   @Nullable QueryType queryType, @Nullable IList<SelectExpression> select,
                                   @Nullable IList<SelectExpression> groupBy, @Nullable IMap<SelectExpression, Order> orderBy,
                                   @Nullable BooleanExpression having, @Nullable Limit limit,
                                   @Nullable IList<IMap<Column, Expression>> set, @Nullable QueryFlags queryFlags,
                                   @Nullable Union union) {
    super(where, fromExpression, queryType, select, groupBy, orderBy, having, limit, set, queryFlags, union);
  }

  public CompleteQuery set(Column column, Literal value) {
    if (set == null) {
      if (this instanceof PartialInsertQuery) {
        return InsertQuery.make(IArrayList.make(IHashMap.make(column, value)), fromExpression);
      } else if (this instanceof PartialUpdateQuery){
        return UpdateQuery.make(IArrayList.make(IHashMap.make(column, value)), fromExpression);
      } else {
        return InsertQuery.make(IArrayList.make(IHashMap.make(column, value)), fromExpression, true);
      }
    }
    final IMap<Column, Expression> map = set.get(0).put(column, value);
    final IList<IMap<Column, Expression>> records = IArrayList.make(map);
    if (this instanceof PartialInsertQuery) {
      return InsertQuery.make(records, fromExpression);
    } else if (this instanceof PartialUpdateQuery){
      return UpdateQuery.make(records, fromExpression);
    } else {
      return InsertQuery.make(records, fromExpression, true);
    }
  }
}
