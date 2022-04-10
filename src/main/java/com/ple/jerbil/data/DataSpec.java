package com.ple.jerbil.data;

import com.ple.util.Immutable;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.Objects;
import java.util.Optional;

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
  public final static DataSpec integer = new DataSpec(DataType.integer, DataType.integer.defaultSize, null);
  public final static DataSpec varchar = new DataSpec(DataType.varchar, DataType.varchar.defaultSize, null);
  public final DataType dataType;
  public final Optional<Integer> size;
  @Nullable public final int[] preciseScale;

  protected DataSpec(DataType dataType, Optional<Integer> size, int[] preciseScale) {
    this.dataType = dataType;
    this.size = size;
    this.preciseScale = preciseScale;
  }

  public static DataSpec make(DataType type, int size) {
    return new DataSpec(type, Optional.of(size), null);
  }

  public static DataSpec make(DataType type, Optional<Integer> size) {
    return new DataSpec(type, size, null);
  }

  public static DataSpec make(DataType type, int scale, int precision) {
    return new DataSpec(type, type.defaultSize, new int[]{scale, precision});
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

  //TODO: Pull this code out of here and place inside MysqlLanguageGenerator, because this is specific to mysql only. Won't necessarily work for other dbms.
  // Just make the size 0 if not specified by the user explicitely.
  public static DataSpec make(DataType type) {
    return new DataSpec(type, type.defaultSize, null);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof DataSpec)) return false;
    DataSpec dataSpec = (DataSpec) o;
    return dataType == dataSpec.dataType && size.equals(dataSpec.size) && Arrays.equals(preciseScale,
        dataSpec.preciseScale);
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
