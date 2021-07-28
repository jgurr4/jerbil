package com.ple.jerbil.sql;

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

}
