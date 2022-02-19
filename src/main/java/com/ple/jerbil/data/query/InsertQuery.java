package com.ple.jerbil.data.query;

import com.ple.jerbil.data.DelayedImmutable;
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
                        @Nullable IList<SelectExpression> groupBy, @Nullable IList<SelectExpression> orderBy,
                        @Nullable IList<BooleanExpression> having, @Nullable Limit limit,
                        @Nullable IList<IMap<Column, Expression>> set, @Nullable boolean mayInsert,
                        @Nullable boolean mayReplace, @Nullable boolean triggerDeleteWhenReplacing,
                        @Nullable boolean mayThrowOnDuplicate) {
    super(where, fromExpression, queryType, select, groupBy, orderBy, having, limit, set, mayInsert, mayReplace,
        triggerDeleteWhenReplacing, mayThrowOnDuplicate);
  }

  public static InsertQuery make(Column column, Literal value, FromExpression fromExpression) {
    return new InsertQuery(null, fromExpression, QueryType.insert, null, null, null,
        null, null, IArrayList.make(IHashMap.make(column, value)), true, false,
        false, false);
  }

  public static InsertQuery make(Column column, Literal value, FromExpression fromExpression, boolean mayReplace) {
    return new InsertQuery(null, fromExpression, QueryType.insert, null, null, null,
        null, null, IArrayList.make(IHashMap.make(column, value)), true, mayReplace,
        false, false);
  }

  public static InsertQuery make(IList<IMap<Column, Expression>> set, FromExpression fromExpression, boolean mayReplace) {
    return new InsertQuery(null, fromExpression, QueryType.insert, null, null, null,
        null, null, set, true, mayReplace, false, false);
  }

  public static InsertQuery make(IList<IMap<Column, Expression>> set, FromExpression fromExpression) {
    return new InsertQuery(null, fromExpression, QueryType.insert, null, null, null,
        null, null, set, true, false, false, false);
  }

}
