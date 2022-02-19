package com.ple.jerbil.data.query;

import com.ple.jerbil.data.Immutable;
import com.ple.jerbil.data.Order;
import com.ple.jerbil.data.selectExpression.*;
import com.ple.jerbil.data.selectExpression.NumericExpression.NumericColumn;
import com.ple.jerbil.data.selectExpression.booleanExpression.BooleanExpression;
import com.ple.util.IArrayList;
import com.ple.util.IList;
import com.ple.util.IMap;
import org.jetbrains.annotations.Nullable;

@Immutable
public class SelectQuery extends CompleteQuery {

  SelectQuery(@Nullable BooleanExpression where, @Nullable FromExpression fromExpression, @Nullable QueryType queryType, @Nullable IList<SelectExpression> select, @Nullable IList<SelectExpression> groupBy, @Nullable IList<SelectExpression> orderBy, @Nullable IList<BooleanExpression> having, @Nullable Limit limit, @Nullable IList<IMap<Column, Expression>> set, @Nullable boolean mayInsert, @Nullable boolean mayReplace, @Nullable boolean triggerDeleteWhenReplacing, @Nullable boolean mayThrowOnDuplicate) {
    super(where, fromExpression, queryType, select, groupBy, orderBy, having, limit, set, mayInsert, mayReplace, triggerDeleteWhenReplacing, mayThrowOnDuplicate);
  }

  public static SelectQuery make(IList<SelectExpression> selectExpressions) {
    return new SelectQuery(null, null, QueryType.select, selectExpressions, null, null, null, null, null, false, false, false, false);
  }

  public static SelectQuery make(Table table, IArrayList<SelectExpression> selectExpressions) {
    return new SelectQuery(null, table, QueryType.select, selectExpressions, null, null, null, null, null, false, false, false, false);
  }

  public static SelectQuery make(BooleanExpression where, FromExpression fromExpression, QueryType queryType, IList<SelectExpression> selectExpressions, IList<SelectExpression> groupBy, IList<SelectExpression> orderBy, IList<BooleanExpression> having, Limit limit, IList<IMap<Column, Expression>> set, boolean mayInsert, boolean mayReplace, boolean triggerDeleteWhenReplacing, boolean mayThrowOnDuplicate) {
    return new SelectQuery(where, fromExpression, queryType, selectExpressions, groupBy, orderBy, having, limit, set, mayInsert, mayReplace, triggerDeleteWhenReplacing, mayThrowOnDuplicate);
  }

  public SelectQuery groupBy(SelectExpression... groupBy) {
    return SelectQuery.make(where, fromExpression, queryType, select, IArrayList.make(groupBy), orderBy, having, limit, set, mayInsert, mayReplace, triggerDeleteWhenReplacing, mayThrowOnDuplicate);
  }

  public CompleteQuery union(SelectQuery select) {
    return null;
  }

  public CompleteQuery unionAll(SelectQuery select) {
    return null;
  }

  public CompleteQuery whereMatch(StringColumn column, LiteralString valueToMatch) {
    return null;
  }

  public CompleteQuery orderBy(SelectExpression select, Order order) {
    return null;
  }

  public SelectQuery having(BooleanExpression having) {
    return null;
  }
}
