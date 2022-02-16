package com.ple.jerbil.data;

import com.ple.jerbil.data.bridge.ReactiveWrapper;
import com.ple.jerbil.data.bridge.SynchronousObject;
import com.ple.jerbil.data.query.CompleteQuery;
import com.ple.jerbil.data.query.CreateQuery;
import com.ple.jerbil.data.query.QueryList;
import com.ple.jerbil.data.query.Table;
import com.ple.util.IArrayList;
import com.ple.util.IList;

/**
 * Database is a object representing the database and it's tables.
 */
@Immutable
public class Database {

  public final String databaseName;
  public final IList<Table> tables;
  public final CharSet charSet;

  protected Database(String databaseName, IList<Table> tables, CharSet charSet) {
    this.databaseName = databaseName;
    this.tables = tables;
    this.charSet = charSet;
  }

  public static Database make(String name) {
    return new Database(name, null, CharSet.utf8mb4);
  }

  public static Database make(String name, IList<Table> tables) {
    return new Database(name, tables, CharSet.utf8mb4);
  }

  public Database add(Table... tables) {
    return new Database(databaseName, IArrayList.make(tables), charSet);
  }

  @Override
  public String toString() {
    return "Database{" +
        "databaseName='" + databaseName + '\'' +
        ", tables=" + tables +
        ", charSet=" + charSet +
        '}';
  }

  public String drop() {
    return null;
  }

  public ReactiveWrapper<Database> wrap() {
    return SynchronousObject.make(this);
  }

  public Database getDatabase() {
    return this;
  }

}
