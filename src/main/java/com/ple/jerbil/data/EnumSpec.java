package com.ple.jerbil.data;

public class EnumSpec extends DataSpec {

  public final String[] values;

  @Immutable
  protected EnumSpec(DataType dataType, int size, String[] values) {
    super(dataType, size);
    this.values = values;
  }

  public static EnumSpec make(DataType type, int size, String[] values) {
    return new EnumSpec(type, size, values);
  }

}
