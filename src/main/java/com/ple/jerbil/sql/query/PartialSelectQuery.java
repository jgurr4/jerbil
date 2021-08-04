package com.ple.jerbil.sql.query;

import com.ple.jerbil.sql.expression.BooleanExpression;
import com.ple.jerbil.sql.expression.SelectExpression;
import com.ple.jerbil.sql.fromExpression.Table;
import org.jetbrains.annotations.Nullable;

public class PartialSelectQuery extends PartialQuery {
  public final SelectExpression condition;

  protected PartialSelectQuery(SelectExpression condition, @Nullable Table table) {
    super(table);
    this.condition = condition;
  }

  public static PartialSelectQuery make(SelectExpression condition) {
    return new PartialSelectQuery(condition, null);
  }

  public PartialSelectQuery where(BooleanExpression condition) {
    return PartialSelectQuery.make(condition);
  }

//  public CompleteQuery select(Expression... expressions) {  // This is used to turn a partialQuery into a CompleteQuery.
//    return CompleteQuery.make(QueryType.select, this.table, expressions);
//  }
}
