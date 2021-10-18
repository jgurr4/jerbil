package com.ple.jerbil.sql.query;

import com.ple.jerbil.sql.Immutable;
import com.ple.jerbil.sql.fromExpression.FromExpression;
import com.ple.jerbil.sql.fromExpression.Table;
import com.ple.jerbil.sql.selectExpression.SelectExpression;
import com.ple.util.IList;
import org.jetbrains.annotations.Nullable;

@Immutable
public class SelectQuery extends CompleteQuery {

  protected SelectQuery(IList<SelectExpression> expressions, @Nullable FromExpression fromExpression, @Nullable Table table) {
    super(table, null, null, null);
  }

  public static SelectQuery make(IList<SelectExpression> expressions, @Nullable FromExpression fromExpression, @Nullable Table table) {
    return new SelectQuery(expressions, fromExpression, table);
  }

  public static SelectQuery make(IList<SelectExpression> expressions) {
    return new SelectQuery(expressions, null, null);
  }
}
