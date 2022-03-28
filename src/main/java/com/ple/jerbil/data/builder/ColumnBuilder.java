package com.ple.jerbil.data.builder;

import com.ple.jerbil.data.query.Table;

public class ColumnBuilder extends AbstractBuilder {
  private DatabaseBuilder dbBuild;
  private TableBuilder tblBuild;
  private String columnName;
  private Table table;

  protected ColumnBuilder(DatabaseBuilder dbBuild, TableBuilder tblBuild, String columnName, Table table) {
    this.dbBuild = dbBuild;
    this.tblBuild = tblBuild;
    this.columnName = columnName;
    this.table = table;
  }

  public DatabaseBuilder getDbBuild() {
    return dbBuild;
  }

  public String getColumnName() {
    return columnName;
  }

  public Table getTable() {
    return table;
  }

  @Override
  public TableBuilder newTable(String tableName) {
    return dbBuild.newTable(tableName);
  }

  @Override
  public PartialColumnBuilder newColumn(String columnName) {
    return tblBuild.newColumn(columnName);
  }
}
