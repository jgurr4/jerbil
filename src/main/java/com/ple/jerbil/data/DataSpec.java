package com.ple.jerbil.data;

import java.util.EnumSet;

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
  /* It's important to note that defaultMaxSize for sql databases refers to default max DISPLAY size.
  * Which for most values is 255. This is a constant used instead of 0 value for unspecified datatype sizes. */
  public final static int defaultMaxSize = 255;

  protected DataSpec(DataType dataType, int size) {
    this.dataType = dataType;
    this.size = size;
  }

  public static DataSpec make(DataType type, int size) {
    return new DataSpec(type, size);
  }

  public static DataSpec make(DataType type, Class enumObj) {
    final EnumSet enumSet = EnumSet.allOf(enumObj);
    String enumStr = "(";
    String separator = "";
    int size = 0;
    for (Object e : enumSet) {
      enumStr += separator + "'" + ((Enum) e).name() + "'";
      separator = ",";
      size++;
    }
    enumStr += ")";
    return EnumSpec.make(type, size, enumStr);
  }

  public static DataSpec make(DataType type) {
/*
    if (type == DataType.varchar || type == DataType.bigint || type == DataType.integer) {
      return new DataSpec(type, defaultMaxSize);
    }
*/
    return new DataSpec(type, defaultMaxSize);
  }

}
