package com.ple.jerbil.data.builder;

import com.ple.jerbil.data.query.Table;
import com.ple.jerbil.data.selectExpression.BooleanColumn;
import com.ple.jerbil.data.selectExpression.booleanExpression.BooleanExpression;

public class BooleanColumnBuilder extends ColumnBuilder {
  private BooleanColumn column;

  protected BooleanColumnBuilder(DatabaseBuilder dbBuild, String columnName, Table table, BooleanColumn column) {
    super(dbBuild, tblBuild, columnName, table);
    this.column = column;
  }

  public static BooleanColumnBuilder make(DatabaseBuilder dbBuild, TableBuilder tblBuild,
                                          BooleanColumn column) {
    return new BooleanColumnBuilder(dbBuild, tblBuild, column.columnName, column.table);
  }

  public BooleanColumn defaultValue(BooleanExpression bool) {
    return new BooleanColumn(columnName, table, dataSpec, bool, hints);
  }

}
