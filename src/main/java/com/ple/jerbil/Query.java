package com.ple.jerbil;

import java.util.List;

@Immutable
public class Query {

  public Query select(Expression... expressions) {
    return null;
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

  public Query where(BooleanExpression condition) {
    return null;
  }

  public Query set(Column name, Literal value) {
    return null;
  }

  public Query set(List<Column> columns, List<List<String>> values) {
    return null;
  }

  public Query and(BooleanExpression expression) {

    return null;
  }

  public Query groupBy(Column name) {

    return null;
  }

  public Expression minus(int i) {

    return null;
  }
  // includes statements and queries. Insert, Update, Delete, Select, and where etc...


}
