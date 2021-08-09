package com.ple.jerbil.sql.expression;

import com.ple.jerbil.sql.DataSpec;
import com.ple.jerbil.sql.fromExpression.Table;
import org.jetbrains.annotations.Nullable;

public class PartialColumn {

  public final String name;
  public final Table table;
  @Nullable public final DataSpec dataSpec;
  public final boolean indexed;
  public final boolean primary;

  protected PartialColumn(String name, Table table, @Nullable DataSpec dataSpec, boolean indexed, boolean primary) {
    this.name = name;
    this.table = table;
    this.dataSpec = dataSpec;
    this.indexed = indexed;
    this.primary = primary;
  }

  public static PartialColumn make(String name, Table table) {
    return new PartialColumn(name, table, null, false, false);
  }

  public Column primary() {
    return null;
  }

  public Column varchar(int size) {
    return null;
  }

  public Column indexed() {
    return null;
  }

  public Column id() {
    // This means the column is int and indexed. Or alternatively it could mean primary key and auto_incremented.
    return null;
  }

  public Column enumOf(Class aClass) {
    return null;
  }

  public Column varchar() {
    return varchar(255);
  }

  public Column integer() {
    return null;
  }

  public Column integer(int size) {
    return null;
  }

}
