package com.ple.jerbil.data.query;

import com.ple.jerbil.data.Immutable;
import com.ple.jerbil.data.selectExpression.Column;
import com.ple.jerbil.data.selectExpression.Expression;
import com.ple.jerbil.data.selectExpression.Literal;
import com.ple.jerbil.data.selectExpression.SelectExpression;
import com.ple.jerbil.data.selectExpression.booleanExpression.BooleanExpression;
import com.ple.util.IHashMap;
import com.ple.util.IList;
import com.ple.util.IMap;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * For use with PartialUpdate, PartialInsert and PartialReplace queries, because they require .set method to list columns and the values to put in/replace.
 */
@Immutable
public class PartialQueryWithValues extends PartialQuery {
  protected PartialQueryWithValues(@Nullable BooleanExpression where, @Nullable FromExpression fromExpression, @Nullable QueryType queryType, @Nullable IList<SelectExpression> select, @Nullable IList<SelectExpression> groupBy, @Nullable IList<SelectExpression> orderBy, @Nullable IList<BooleanExpression> having, @Nullable Limit limit, @Nullable IMap<Column, Expression> set, @Nullable boolean mayInsert, @Nullable boolean mayReplace, @Nullable boolean triggerDeleteWhenReplacing, @Nullable boolean mayThrowOnDuplicate) {
    super(where, fromExpression, queryType, select, groupBy, orderBy, having, limit, set, mayInsert, mayReplace, triggerDeleteWhenReplacing, mayThrowOnDuplicate);
  }

  public CompleteQuery set(List<Column> columns, List<List<String>> values) {
    return null;
  }

  public CompleteQuery set(Column column, Literal value) {
    return CompleteQuery.make(IHashMap.from(column, value));
  }

}
