package com.ple.jerbil.sql;

@Immutable
public class DataSpec {

  public final static DataSpec integer = new DataSpec(DataType.integer, 0);
  public final static DataSpec varchar = new DataSpec(DataType.varchar, 0);
  public final DataType dataType;
  public final int size;

  protected DataSpec(DataType dataType, int size) {
    this.dataType = dataType;
    this.size = size;
  }

  public static DataSpec make(DataType type, int size) {

    return null;
  }

  public static DataSpec make(DataType type) {

    return null;
  }

}
