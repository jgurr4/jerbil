package com.ple.jerbil.data.query;

import com.ple.jerbil.data.Immutable;
import com.ple.jerbil.data.selectExpression.Column;
import com.ple.jerbil.data.selectExpression.Expression;
import com.ple.jerbil.data.selectExpression.SelectExpression;
import com.ple.jerbil.data.selectExpression.booleanExpression.BooleanExpression;
import com.ple.util.IArrayList;
import com.ple.util.IList;
import com.ple.util.IMap;
import org.jetbrains.annotations.Nullable;

@Immutable
public class SelectQuery extends CompleteQuery {

  SelectQuery(@Nullable BooleanExpression where, @Nullable FromExpression fromExpression, @Nullable QueryType queryType, @Nullable IList<SelectExpression> select, @Nullable IList<SelectExpression> groupBy, @Nullable IList<SelectExpression> orderBy, @Nullable IList<BooleanExpression> having, @Nullable Limit limit, @Nullable IMap<Column, Expression> set, @Nullable boolean mayInsert, @Nullable boolean mayReplace, @Nullable boolean triggerDeleteWhenReplacing, @Nullable boolean mayThrowOnDuplicate) {
    super(where, fromExpression, queryType, select, groupBy, orderBy, having, limit, set, mayInsert, mayReplace, triggerDeleteWhenReplacing, mayThrowOnDuplicate);
  }

  public static SelectQuery make(IList<SelectExpression> selectExpressions) {
    return new SelectQuery(null, null, QueryType.select, selectExpressions, null, null, null, null, null, false, false, false, false);
  }

  public static SelectQuery make(BooleanExpression where, FromExpression fromExpression, QueryType queryType, IList<SelectExpression> selectExpressions, IList<SelectExpression> groupBy, IList<SelectExpression> orderBy, IList<BooleanExpression> having, Limit limit, IMap<Column, Expression> set, boolean mayInsert, boolean mayReplace, boolean triggerDeleteWhenReplacing, boolean mayThrowOnDuplicate) {
    return new SelectQuery(where, fromExpression, queryType, selectExpressions, groupBy, orderBy, having, limit, set, mayInsert, mayReplace, triggerDeleteWhenReplacing, mayThrowOnDuplicate);
  }
}