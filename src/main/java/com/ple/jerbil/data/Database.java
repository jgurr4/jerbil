package com.ple.jerbil.data;

import com.ple.util.Immutable;

/**
 * Database is a object representing the database and it's tables.
 */
@Immutable
public class Database {
  public final String databaseName;

  protected Database(String databaseName) {
    this.databaseName = databaseName;
  }

  public static Database make(String name) {
    return new Database(name);
  }

  @Override
  public String toString() {
    return "Database{" +
        "databaseName='" + databaseName + '\'' +
        '}';
  }


}
