package com.ple.jerbil.data.builder;

import com.ple.jerbil.data.Database;

import java.util.ArrayList;
import java.util.List;

//This is a Framework class that we provide the user. This does not need to be immutable, it should be mutable because it's a pre-compile time class.
// Only runtime methods/objects need to be immutable.
public class DatabaseBuilder extends AbstractBuilder{
  private Database db;
  private List<TableBuilder> tables;

  public DatabaseBuilder(Database db, List<TableBuilder> tables) {
    this.db = db;
    this.tables = tables;
  }

  public static DatabaseBuilder make(String dbName) {
    return new DatabaseBuilder(Database.make(dbName), new ArrayList<>());
  }

  @Override
  public TableBuilder newTable(String tableName) {
    TableBuilder tb = TableBuilder.make(tableName, this);
    tables.add(tb);
    return tb;
  }

  @Override
  public PartialColumnBuilder newColumn(String columnName) {
    return tables.get(tables.size()-1).newColumn(columnName);
  }

  public List<TableBuilder> getTables() {
    return tables;
  }

  public Database getDb() {
    return db;
  }
}
