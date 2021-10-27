package com.ple.jerbil.sql.query;

import com.ple.jerbil.sql.Immutable;
import com.ple.jerbil.sql.selectExpression.booleanExpression.BooleanExpression;
import com.ple.jerbil.sql.selectExpression.SelectExpression;
import com.ple.util.IList;
import org.jetbrains.annotations.Nullable;

/**
 * NOTE: Table.select() method will always return a CompleteQuery because technically that is a CompleteQuery, but
 * it still allows you to append .where() and other similar clauses/methods because CompleteQuery class has where(),
 * having() and other similar methods. So at this time, PartialSelectQuery is never used.
 */
@Immutable
public class PartialSelectQuery extends PartialQuery {

  protected PartialSelectQuery(IList<SelectExpression> selectExpressions, @Nullable FromExpression fromExpression) {
    super(null, fromExpression, null, selectExpressions, null, null, null, null, null, false, false, false, false);
  }

  public static PartialSelectQuery make(IList<SelectExpression> selectExpressions) {
    return new PartialSelectQuery(selectExpressions, null);
  }

  public PartialSelectQuery where(BooleanExpression condition) {
    return null;
  }

//  public CompleteQuery select(Expression... expressions) {  // This is used to turn a partialQuery into a CompleteQuery.
//    return CompleteQuery.make(QueryType.select, this.table, expressions);
//  }
}
