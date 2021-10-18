package com.ple.jerbil.sql.query;

import com.ple.jerbil.sql.fromExpression.FromExpression;
import com.ple.jerbil.sql.selectExpression.booleanExpression.BooleanExpression;
import com.ple.jerbil.sql.selectExpression.SelectExpression;
import com.ple.jerbil.sql.fromExpression.Table;
import com.ple.util.IList;
import org.jetbrains.annotations.Nullable;

public class PartialSelectQuery extends PartialQuery {

  protected PartialSelectQuery(IList<SelectExpression> select, @Nullable FromExpression fromExpression) {
    super(null, fromExpression, null, select, null, null, null, null, null, false, false, false, false);
  }

  public static PartialSelectQuery make(IList<SelectExpression> condition) {
    return new PartialSelectQuery(condition, null);
  }

  public PartialSelectQuery where(BooleanExpression condition) {
    return null;
  }

//  public CompleteQuery select(Expression... expressions) {  // This is used to turn a partialQuery into a CompleteQuery.
//    return CompleteQuery.make(QueryType.select, this.table, expressions);
//  }
}
