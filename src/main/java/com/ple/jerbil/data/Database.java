package com.ple.jerbil.data;

import com.ple.jerbil.data.query.QueryList;
import com.ple.jerbil.data.query.Table;

/**
 * Database is a object representing the database and it's tables.
 */
@DelayedImmutable
public class Database {

  private final String name;

  public Database(String name) {
    this.name = name;
  }

  public static Database make(String name) {
    return new Database(name);
  }

  public Database add(Table... tables) {
    return null;
  }

  public String toSql() {
    return null;
  }

  public QueryList createAll() {
    return null;
  }

}
