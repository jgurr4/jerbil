package com.ple.jerbil.data.query;

import com.ple.jerbil.data.Database;
import com.ple.jerbil.data.DelayedImmutable;
import com.ple.jerbil.data.Order;
import com.ple.jerbil.data.selectExpression.Column;
import com.ple.jerbil.data.selectExpression.Expression;
import com.ple.jerbil.data.selectExpression.SelectExpression;
import com.ple.jerbil.data.selectExpression.booleanExpression.BooleanExpression;
import com.ple.util.IList;
import com.ple.util.IMap;
import org.jetbrains.annotations.Nullable;

@DelayedImmutable
public class CreateQuery extends CompleteQuery {

  public final Database db;

  protected CreateQuery(@Nullable BooleanExpression where, @Nullable FromExpression fromExpression,
                        @Nullable QueryType queryType, @Nullable IList<SelectExpression> select,
                        @Nullable IList<SelectExpression> groupBy, @Nullable IMap<SelectExpression, Order> orderBy,
                        @Nullable IList<BooleanExpression> having, @Nullable Limit limit,
                        @Nullable IList<IMap<Column, Expression>> set, @Nullable InsertFlags insertFlags, @Nullable Database db) {
    super(where, fromExpression, queryType, select, groupBy, orderBy, having, limit, set, insertFlags);
    this.db = db;
  }

  public static CreateQuery make(TableContainer tableContainer) {
    return new CreateQuery(null, tableContainer, QueryType.create, null, null, null, null, null, null, null, null);
  }

  public static CompleteQuery make(Database db) {
    return new CreateQuery(null, null, QueryType.create, null, null, null, null, null, null, null, db);
  }

}
