package com.ple.jerbil.sql.query;

import com.ple.jerbil.sql.selectExpression.Column;
import com.ple.jerbil.sql.selectExpression.Expression;
import com.ple.jerbil.sql.selectExpression.SelectExpression;
import com.ple.jerbil.sql.selectExpression.booleanExpression.BooleanExpression;
import com.ple.util.IList;
import com.ple.util.IMap;
import org.jetbrains.annotations.Nullable;

public class DeleteQuery extends CompleteQuery {

  protected DeleteQuery(@Nullable IList<BooleanExpression> where, @Nullable FromExpression fromExpression, @Nullable QueryType queryType, @Nullable IList<SelectExpression> select, @Nullable IList<SelectExpression> groupBy, @Nullable IList<SelectExpression> orderBy, @Nullable IList<BooleanExpression> having, @Nullable Limit limit, @Nullable IMap<Column, Expression> set, @Nullable boolean mayInsert, @Nullable boolean mayReplace, @Nullable boolean triggerDeleteWhenReplacing, @Nullable boolean mayThrowOnDuplicate) {
    super(where, fromExpression, queryType, select, groupBy, orderBy, having, limit, set, mayInsert, mayReplace, triggerDeleteWhenReplacing, mayThrowOnDuplicate);
  }
}
