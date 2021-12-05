package com.ple.jerbil.data.query;

import com.ple.jerbil.data.Immutable;
import com.ple.jerbil.data.selectExpression.Column;
import com.ple.jerbil.data.selectExpression.Expression;
import com.ple.jerbil.data.selectExpression.booleanExpression.BooleanExpression;
import com.ple.jerbil.data.selectExpression.SelectExpression;
import com.ple.util.IArrayList;
import com.ple.util.IList;
import com.ple.util.IMap;
import org.jetbrains.annotations.Nullable;


@Immutable
public class QueryWithFrom extends PartialQuery {


  protected QueryWithFrom(@Nullable BooleanExpression where, @Nullable FromExpression fromExpression, @Nullable QueryType queryType, @Nullable IList<SelectExpression> select, @Nullable IList<SelectExpression> groupBy, @Nullable IList<SelectExpression> orderBy, @Nullable IList<BooleanExpression> having, @Nullable Limit limit, @Nullable IMap<Column, Expression> set, @Nullable boolean mayInsert, @Nullable boolean mayReplace, @Nullable boolean triggerDeleteWhenReplacing, @Nullable boolean mayThrowOnDuplicate) {
    super(where, fromExpression, queryType, select, groupBy, orderBy, having, limit, set, mayInsert, mayReplace, triggerDeleteWhenReplacing, mayThrowOnDuplicate);
  }

  public static QueryWithFrom make(FromExpression fromExpression) {
    return new QueryWithFrom(null, fromExpression, null, null, null, null, null, null, null, false, false, false, false);
  }

  public SelectQuery selectAll() {
    return SelectQuery.make(this.where, this.fromExpression, QueryType.select, IArrayList.make(SelectExpression.selectAll), this.groupBy, this.orderBy, this.having, this.limit, this.set, this.mayInsert, this.mayReplace, this.triggerDeleteWhenReplacing, this.mayThrowOnDuplicate);
  }

  public QueryWithFrom where(BooleanExpression where) {
    return new QueryWithFrom(where, this.fromExpression, null, null, null, null, null, null, null, false, false, false, false);
  }

}
