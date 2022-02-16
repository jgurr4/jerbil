package com.ple.jerbil.data.query;

import com.ple.jerbil.data.selectExpression.Column;
import com.ple.util.IList;

public class TableContainer<Table> {
  public final Table table;
  public final IList<Column> columns;

  protected TableContainer(Table table, IList<Column> columns) {
    this.table = table;
    this.columns = columns;
  }

  public static <Table> TableContainer make(Table table, IList<Column> columns) {
    return new TableContainer(table, columns);
  }
}
