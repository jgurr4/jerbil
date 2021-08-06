package com.ple.jerbil.sql;

import com.ple.jerbil.sql.fromExpression.Table;

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

}
