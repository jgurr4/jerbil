package com.ple.jerbil.sql;

/**
 * DataSpec represents any datatype and it's parameter definitions for defining specific columns in tables. For example:
 * create table example (
 *   id int(11),
 *   name varchar(20),
 *   salutation enum("hello","goodbye"),
 *   affected set("hair","face","body"),
 *   price decimal(14,2),
 *   creation_date date,
 *   time_since_update time,
 * );
 * After each column name specified above is the dataspec for that column.
 */
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
    // depending on the datatype, it will return different default sizes for constructor.
    return null;
  }

}
