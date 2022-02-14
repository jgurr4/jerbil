package com.ple.jerbil.data;

@Immutable
public class DbName {

  private final String name;

  protected DbName(String name) {
    this.name = name;
  }

  public static DbName make(String name) {
    return new DbName(name);
  }

  public String get() {
    return name;
  }

}
