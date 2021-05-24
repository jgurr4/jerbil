package com.ple.jerbil;

public class Column extends Expression {

  public final String name;
  public Table table; // This should only be set 1 time and never changed. Semi-immutable.

  public Column(String columnName) {
    this.name = columnName;

  }

  public static Column make(String columnName) {

    return new Column(columnName);
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
