package com.ple.jerbil;

public abstract class Table {

  protected StorageEngine engine = StorageEngine.simple;
  private final String tableName;

  public Table(String name) {
    this.tableName = name;
  }

}
