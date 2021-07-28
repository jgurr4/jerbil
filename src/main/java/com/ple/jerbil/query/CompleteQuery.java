package com.ple.jerbil.query;

import com.ple.jerbil.Global;
import com.ple.jerbil.Immutable;
import com.ple.jerbil.SqlGenerator;
import com.ple.jerbil.Table;
import com.ple.jerbil.expression.BooleanExpression;
import com.ple.jerbil.expression.Column;
import com.ple.jerbil.expression.Expression;
import com.ple.jerbil.expression.SelectExpression;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Immutable
public class CompleteQuery extends Query {

  private final QueryType type;
  private final Table table;
  private List<Map<Column, Expression>> dataToInsert;

  private CompleteQuery(QueryType type, Table table, List<Map<Column, Expression>> dataToInsert) {
    this.type = type;
    this.table = table;
    this.dataToInsert = dataToInsert;
  }

  public static CompleteQuery make(QueryType type, Table table) {
    return new CompleteQuery(table);
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

  public CompleteQuery set(Column name, Expression expression) {

    Map<Column, Expression> map;
    if (dataToInsert.size() == 0 ) {
      map = new HashMap<>();
      dataToInsert.add(map);
    } else {
      map = dataToInsert.get(dataToInsert.size() - 1);
    }
    map.put(name, expression);
    return new CompleteQuery(type, dataToInsert);
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
