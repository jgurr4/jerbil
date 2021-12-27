package com.ple.jerbil.data;

import org.jetbrains.annotations.Nullable;

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

  public final static DataSpec integer = new DataSpec(DataType.integer, 0, null);
  public final static DataSpec varchar = new DataSpec(DataType.varchar, 0, null);
  public final DataType dataType;
  public final int size;
  @Nullable public final int[] preciseScale;
  public final static int defaultMaxSize = 255;

  protected DataSpec(DataType dataType, int size, int[] preciseScale) {
    this.dataType = dataType;
    this.size = size;
    this.preciseScale = preciseScale;
  }

  public DataSpec(DataType dataType, int size) {
    this.dataType = dataType;
    this.size = size;
    this.preciseScale = null;
  }

  public static DataSpec make(DataType type, int size) {
    return new DataSpec(type, size, null);
  }

  public static DataSpec make(DataType type, int precision, int scale) {
    return new DataSpec(type, defaultMaxSize, new int[]{precision, scale});
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
    return new DataSpec(type, defaultMaxSize, null);
  }

  public String getSqlName() {
    if (this.dataType.name() == "integer") {
      return "int";
    } else if (this.dataType.name() == "bool") {
      return "boolean";
    } else if (this.dataType.name() == "enumeration") {
      return "enum";
    }
    return this.dataType.name();
  }

}
