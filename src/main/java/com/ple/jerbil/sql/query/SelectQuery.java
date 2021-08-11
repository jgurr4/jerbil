package com.ple.jerbil.sql.query;

import com.ple.jerbil.sql.Immutable;
import com.ple.jerbil.sql.fromExpression.FromExpression;
import com.ple.jerbil.sql.fromExpression.Table;
import com.ple.jerbil.sql.expression.SelectExpression;
import com.ple.util.IList;
import org.jetbrains.annotations.Nullable;

@Immutable
public class SelectQuery extends CompleteQuery {
  public final IList<SelectExpression> expressions;

  protected SelectQuery(IList<SelectExpression> expressions, @Nullable FromExpression fromExpression) {
    super(fromExpression);
    this.expressions = expressions;
  }

  public static SelectQuery make(IList<SelectExpression> expressions, @Nullable FromExpression table) {
    return new SelectQuery(expressions, table);
  }

  public static SelectQuery make(IList<SelectExpression> expressions) {
    return new SelectQuery(expressions, null);
  }
}