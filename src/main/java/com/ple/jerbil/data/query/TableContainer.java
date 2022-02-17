package com.ple.jerbil.data.query;

import com.ple.jerbil.data.selectExpression.Column;
import com.ple.jerbil.data.selectExpression.ColumnService;
import com.ple.jerbil.data.sync.SyncResult;
import com.ple.util.IArrayList;
import com.ple.util.IList;
import com.ple.util.IMap;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

public class TableContainer {
  public final Table table;
  public final IMap<String, Column> columns;

  protected TableContainer(Table table, IMap<String, Column> columns) {
    this.table = table;
    this.columns = columns;
  }

  public static TableContainer make(Table table, IMap<String, Column> columns) {
    return new TableContainer(table, columns);
  }

  public SyncResult sync() {
    return null;
  }

  public SelectQuery select() {
    return null;
  }

//  public static <T extends Table, T2> TableContainer make(T table, T2 customColumns) {
//    final IList<Column> columns = ColumnService.asList(customColumns);
//    return new TableContainer(table, columns);
//  }
}
