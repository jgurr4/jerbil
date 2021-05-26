package com.ple.jerbil;

import java.awt.*;

@DelayedImmutable
public abstract class Table {

  protected StorageEngine engine = StorageEngine.simple;
  private final String tableName;

  public Table(String name) {
    this.tableName = name;
  }

  public Query where(BooleanExpression condition) {

    return null;
  }

  public Query select(Expression... expression) {

    return null;
  }

  public Query join(Table... tables) {
    return null;
  }

  public Query insert() {
    return Query.from(QueryType.insert);
  }

  public Query create() {
    return null;
  }

}
