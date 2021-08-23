package com.ple.jerbil.sql.expression;

import com.ple.jerbil.sql.DataSpec;
import com.ple.jerbil.sql.DataType;
import com.ple.jerbil.sql.fromExpression.Table;
import org.jetbrains.annotations.Nullable;

public class PartialColumn extends Expression {

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
    if (this.dataSpec == null) {
      return new Column(this.name, this.table, DataSpec.make(DataType.integer), this.indexed, true);
    } else {
      return new Column(this.name, this.table, this.dataSpec, this.indexed, true);
    }
  }

  public Column id() {
    // This means the column is int and indexed. Or alternatively it could mean primary key and auto_incremented.
    return null;
  }

  public Column enumOf(Class aClass) {
    return null;
  }

  public Column integer() {
    return null;
  }

  public Column integer(int size) {
    return null;
  }

  public Column varchar(int size) {
    return new Column(this.name, this.table, DataSpec.make(DataType.varchar, size), this.indexed, this.primary);
  }

  public Column varchar() {
    return new Column(this.name, this.table, DataSpec.make(DataType.varchar, 255), this.indexed, this.primary);
  }

  public Column indexed() {
    return new Column(this.name, this.table, this.dataSpec, true, this.primary);
  }

}
