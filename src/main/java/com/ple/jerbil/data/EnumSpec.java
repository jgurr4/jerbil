package com.ple.jerbil.data;

public class EnumSpec extends DataSpec {

  public final String enumStr;

  @Immutable
  protected EnumSpec(DataType dataType, int size, String enumStr) {
    super(dataType, size);
    this.enumStr = enumStr;
  }

  public static EnumSpec make(DataType type, int size, String enumStr) {
    return new EnumSpec(type, size, enumStr);
  }

}
