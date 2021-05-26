package com.ple.jerbil;

@DelayedImmutable
public class Column extends Expression {

  public final String name;
  public Table table; // This should only be set 1 time and never changed. Semi-immutable.
  private DataSpec dataSpec;
  private boolean indexed;
  private boolean primary;

  public Column(String columnName) {
    this.name = columnName;

  }

  public static Column make(String columnName) {

    return new Column(columnName);
  }

  public Column primary() {
    this.primary = true;
    return this;
  }

  public Column varchar(int size) {
    this.dataSpec = DataSpec.from(DataType.varchar, size);
    return this;
  }

  public Column indexed() {
    this.indexed = true;
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
    return null;
  }

  public BooleanExpression eq(String john) {
    return null;
  }

}
