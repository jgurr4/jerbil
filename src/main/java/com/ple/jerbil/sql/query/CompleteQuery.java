package com.ple.jerbil.sql.query;

import com.ple.jerbil.sql.Global;
import com.ple.jerbil.sql.Immutable;
import com.ple.jerbil.sql.SqlGenerator;
import com.ple.jerbil.sql.fromExpression.FromExpression;
import com.ple.jerbil.sql.fromExpression.Table;
import com.ple.jerbil.sql.expression.BooleanExpression;
import com.ple.jerbil.sql.expression.Column;
import com.ple.jerbil.sql.expression.Expression;
import org.jetbrains.annotations.Nullable;

@Immutable
public class CompleteQuery extends Query {

  protected CompleteQuery(@Nullable FromExpression table) {
    super(table);
  }

  public static CompleteQuery make() {
    return new CompleteQuery(null);
  }

  public String toSql() {
    if (Global.sqlGenerator == null) {
      throw new NullPointerException("Global.sqlGenerator not set.");
    }
    return toSql(Global.sqlGenerator);
  }

  public String toSql(SqlGenerator generator) {
    return null;
  }

  public CompleteQuery where(BooleanExpression condition) {
    return null;
  }

  public CompleteQuery and(BooleanExpression expression) {
    return null;
  }

  public CompleteQuery groupBy(Column name) {
    return null;
  }

  public Expression minus(int i) {
    return null;
  }


}
