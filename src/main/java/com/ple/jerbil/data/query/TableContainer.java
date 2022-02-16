package com.ple.jerbil.data.query;

import com.ple.jerbil.data.selectExpression.Column;
import com.ple.jerbil.data.selectExpression.ColumnService;
import com.ple.util.IArrayList;
import com.ple.util.IList;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

public class TableContainer {
  public final Table table;
  public final IList<Column> columns;

  protected TableContainer(Table table, IList<Column> columns) {
    this.table = table;
    this.columns = columns;
  }

  public static TableContainer make(Table table, IList<Column> columns) {
    return new TableContainer(table, columns);
  }

  public static <T extends Table, T2> TableContainer make(T table, T2 customColumns) {
    final IList<Column> columns = ColumnService.asList(customColumns);
    return new TableContainer(table, columns);
  }
}
