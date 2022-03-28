package com.ple.jerbil.data.builder;

import com.ple.jerbil.data.query.Table;
import java.util.ArrayList;
import java.util.List;

public class TableBuilder extends AbstractBuilder {
  private DatabaseBuilder dbBuild;
  private Table table;
  private List<ColumnBuilder> columns;

  protected TableBuilder(DatabaseBuilder dbBuild, Table table, List<ColumnBuilder> columns) {
    this.dbBuild = dbBuild;
    this.table = table;
    this.columns = columns;
  }

  public static TableBuilder make(String tableName, DatabaseBuilder dbBuild) {
    return new TableBuilder(dbBuild, Table.make(tableName, dbBuild.getDb()), new ArrayList<>());
  }

  @Override
  public TableBuilder newTable(String tableName) {
    return dbBuild.newTable(tableName);
  }

  @Override
  public PartialColumnBuilder newColumn(String columnName) {
    PartialColumnBuilder pc = PartialColumnBuilder.make(dbBuild, this, columnName, table);
    columns.add(pc);
    return pc;
  }
}
