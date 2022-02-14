package com.ple.jerbil.data.query;

import com.ple.jerbil.data.DbName;
import com.ple.jerbil.data.Immutable;

@Immutable
public class TableName {

  private final String name;

  protected TableName(String name) {
    this.name = name;
  }

  public static TableName make(String name) {
    return new TableName(name);
  }

  public String get() {
    return name;
  }
}
