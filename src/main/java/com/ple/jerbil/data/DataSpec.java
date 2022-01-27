package com.ple.jerbil.data;

import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.Objects;

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
    String[] enums = new String[enumSet.size()];
    int i = 0;
    for (Object o : enumSet) {
      enums[i] = o.toString();
      i++;
    }
    return EnumSpec.make(type, enums.length, enums);
  }

  public static DataSpec make(DataType type, String enumStr) {
    final String[] enums = enumStr.split(",");
    return EnumSpec.make(type, enums.length, enums);
  }

  public static DataSpec make(DataType type) {
/*
    if (type == DataType.varchar || type == DataType.bigint || type == DataType.integer) {
      return new DataSpec(type, defaultMaxSize);
    }
*/
    int maxSize = 0;
    if (type == DataType.varchar || type == DataType.enumeration) {
      maxSize = DefaultSize.varchar.getSize();
    } else if (type == DataType.bigint) {
      maxSize = DefaultSize.bigint.getSize();
    } else if (type == DataType.mediumint) {
      maxSize = DefaultSize.mediumint.getSize();
    } else if (type == DataType.integer) {
      maxSize = DefaultSize.integer.getSize();
    } else if (type == DataType.tinyint) {
      maxSize = DefaultSize.tinyint.getSize();
    } else if (type == DataType.bool) {
      maxSize = DefaultSize.bool.getSize();
    }
    return new DataSpec(type, maxSize, null);
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

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof DataSpec)) return false;
    DataSpec dataSpec = (DataSpec) o;
    return size == dataSpec.size && dataType == dataSpec.dataType && Arrays.equals(preciseScale, dataSpec.preciseScale);
  }

  @Override
  public int hashCode() {
    int result = Objects.hash(dataType, size);
    result = 31 * result + Arrays.hashCode(preciseScale);
    return result;
  }

  @Override
  public String toString() {
    return "DataSpec{" +
      "dataType=" + dataType +
      ", size=" + size +
      ", preciseScale=" + Arrays.toString(preciseScale) +
      '}';
  }

}
