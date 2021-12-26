package com.ple.jerbil.data.query;

import com.ple.jerbil.data.Database;
import com.ple.jerbil.data.DelayedImmutable;
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

  protected CreateQuery(@Nullable BooleanExpression where, @Nullable FromExpression fromExpression, @Nullable QueryType queryType, @Nullable IList<SelectExpression> select, @Nullable IList<SelectExpression> groupBy, @Nullable IList<SelectExpression> orderBy, @Nullable IList<BooleanExpression> having, @Nullable Limit limit, @Nullable IList<IMap<Column, Expression>> set, @Nullable boolean mayInsert, @Nullable boolean mayReplace, @Nullable boolean triggerDeleteWhenReplacing, @Nullable boolean mayThrowOnDuplicate, @Nullable Database db) {
    super(where, fromExpression, queryType, select, groupBy, orderBy, having, limit, set, mayInsert, mayReplace, triggerDeleteWhenReplacing, mayThrowOnDuplicate);
    this.db = db;
  }

  public static CreateQuery make(Table table) {
    return new CreateQuery(null, table, QueryType.create, null, null, null, null, null, null, false, false, false, false, null);
  }

  public static CompleteQuery make(Database db) {
    return new CreateQuery(null, null, QueryType.create, null, null, null, null, null, null, false, false, false, false, db);
  }

}
