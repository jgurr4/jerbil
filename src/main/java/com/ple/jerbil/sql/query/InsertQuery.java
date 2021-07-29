package com.ple.jerbil.sql.query;

import com.ple.jerbil.sql.Immutable;
import com.ple.jerbil.sql.Table;
import com.ple.jerbil.sql.expression.Column;
import com.ple.jerbil.sql.expression.Expression;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Immutable
public class InsertQuery extends CompleteQuery {
  private final List<Map<Column, Expression>> dataToInsert;
  private final Table table;
  private final QueryType type;

  private InsertQuery(List<Map<Column, Expression>> dataToInsert, Table table, QueryType type) {
    this.dataToInsert = dataToInsert;
    this.table = table;
    this.type = type;
  }

  public static InsertQuery make(List<Map<Column, Expression>> dataToInsert, Table table, QueryType type) {
    return new InsertQuery(dataToInsert, table, type);
  }

  public InsertQuery set(Column column, Expression expression) {
    List<Map<Column, Expression>> newDataToInsert = dataToInsert;
    Map<Column, Expression> map;
    if (newDataToInsert.size() == 0 ) {
      map = new HashMap<>();
      newDataToInsert.add(map);
    } else {
      map = newDataToInsert.get(newDataToInsert.size() - 1);
    }
    map.put(column, expression);
    return InsertQuery.make(newDataToInsert, table, type);
  }

  public InsertQuery set(List<Column> columns, List<List<String>> values) {
    return null;
  }

}
