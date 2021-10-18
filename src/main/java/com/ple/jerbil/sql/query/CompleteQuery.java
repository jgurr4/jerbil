package com.ple.jerbil.sql.query;

import com.ple.jerbil.sql.Global;
import com.ple.jerbil.sql.Immutable;
import com.ple.jerbil.sql.SqlGenerator;
import com.ple.jerbil.sql.selectExpression.Literal;
import com.ple.jerbil.sql.fromExpression.Table;
import com.ple.jerbil.sql.selectExpression.booleanExpression.BooleanExpression;
import com.ple.jerbil.sql.selectExpression.Column;
import com.ple.jerbil.sql.selectExpression.Expression;

@Immutable
public class CompleteQuery extends Query {

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
