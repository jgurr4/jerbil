package com.ple.jerbil.sql.query;

import com.ple.jerbil.sql.Global;
import com.ple.jerbil.sql.Immutable;
import com.ple.jerbil.sql.SqlGenerator;
import com.ple.jerbil.sql.expression.Literal;
import com.ple.jerbil.sql.fromExpression.FromExpression;
import com.ple.jerbil.sql.fromExpression.Table;
import com.ple.jerbil.sql.expression.BooleanExpression;
import com.ple.jerbil.sql.expression.Column;
import com.ple.jerbil.sql.expression.Expression;
import org.jetbrains.annotations.Nullable;

@Immutable
public class CompleteQuery extends Query {

  public final FromExpression fromExpression;
  public final Column column;
  public final Literal value;

  protected CompleteQuery(FromExpression fromExpression, Column column, Literal value) {
    this.fromExpression = fromExpression;
    this.column = column;
    this.value = value;
  }

  public static CompleteQuery make(Table table) {
    return new CompleteQuery(table, null, null);
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


  public CompleteQuery set(Column column, Literal value) {
    return null;
  }

}
