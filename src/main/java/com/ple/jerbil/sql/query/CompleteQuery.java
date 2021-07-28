package com.ple.jerbil.sql.query;

import com.ple.jerbil.sql.*;
import com.ple.jerbil.sql.expression.BooleanExpression;
import com.ple.jerbil.sql.expression.Column;
import com.ple.jerbil.sql.expression.Expression;
import com.ple.jerbil.sql.expression.SelectExpression;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Immutable
public class CompleteQuery extends Query {  // A complete Query could either be 'select/update/delete/insert expression from table [optional where condition]'. Or it could just be 'select expression'

  private final QueryType type;
  private final Table table;
  private List<Map<Column, Expression>> dataToInsert;

  private CompleteQuery(QueryType type, Table table, List<Map<Column, Expression>> dataToInsert) {
    this.type = type;
    this.table = table;
    this.dataToInsert = dataToInsert;
  }

  public static CompleteQuery make(QueryType type, Table table, List<Map<Column, Expression>> dataToInsert) {
    return new CompleteQuery(type, table, dataToInsert);
  }

  public CompleteQuery select(SelectExpression... expressions) {
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

  public CompleteQuery where(BooleanExpression condition) {
    return null;
  }

  public CompleteQuery set(Column column, Expression expression) {

    List<Map<Column, Expression>> newDataToInsert = dataToInsert;
    Map<Column, Expression> map;
    if (dataToInsert.size() == 0 ) {
      map = new HashMap<>();
      newDataToInsert.add(map);
    } else {
      map = newDataToInsert.get(newDataToInsert.size() - 1);
    }
    map.put(column, expression);
    return CompleteQuery.make(type, table, newDataToInsert);

  }

  public CompleteQuery set(List<Column> columns, List<List<String>> values) {
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
  // includes statements and queries. Insert, Update, Delete, Select, and where etc...


}
