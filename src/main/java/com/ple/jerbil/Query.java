package com.ple.jerbil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Immutable
public class Query {

  private final QueryType type;
  private List<Map<Column, Expression>> dataToInsert;

  public Query(QueryType type) {
    this.type = type;

  }

  public Query(QueryType type, List<Map<Column, Expression>> dataToInsert) {

    this.type = type;
    this.dataToInsert = dataToInsert;
  }

  public static Query from(QueryType type) {

    return new Query(type);
  }

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

  public Query set(Column name, Expression expression) {

    Map<Column, Expression> map;
    if (dataToInsert.size() == 0 ) {
      map = new HashMap<>();
      dataToInsert.add(map);
    } else {
      map = dataToInsert.get(dataToInsert.size() - 1);
    }
    map.put(name, expression);
    return new Query(type, dataToInsert);
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
