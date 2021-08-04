package com.ple.jerbil.sql.expression;

import com.ple.jerbil.sql.DataSpec;
import com.ple.jerbil.sql.DelayedImmutable;
import com.ple.jerbil.sql.fromExpression.Table;

@DelayedImmutable
public class Column extends Expression {

  public final String name;
  public final Table table;
  public final DataSpec dataSpec;
  public final boolean indexed;
  public final boolean primary;

  protected Column(String name, Table table, DataSpec dataSpec, boolean indexed, boolean primary) {
    this.name = name;
    this.table = table;
    this.dataSpec = dataSpec;
    this.indexed = indexed;
    this.primary = primary;
  }

  public static Column make(String name, Table table, DataSpec dataSpec, boolean indexed, boolean primary) {
    return new Column(name, table, dataSpec, indexed, primary);
  }

  public Column primary() {
//    this.primary = true;
    return this;
  }

  public Column varchar(int size) {
//    this.dataSpec = DataSpec.make(DataType.varchar, size);
    return this;
  }

  public Column indexed() {
//    this.indexed = true;
    return this;
  }

  public Column id() {
    return null;
  }

  public Column enumOf(Class aClass) {
    return null;
  }

  public Column varchar() {
    return varchar(255);
  }

  public Column integer() {
//    this.dataSpec = DataSpec.make(DataType.integer);
    return this;
  }

}
