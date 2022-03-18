package com.ple.jerbil.data;

import com.ple.util.Immutable;

import java.util.Arrays;
import java.util.Optional;

public class EnumSpec extends DataSpec {

  public final String[] values;

  @Immutable
  protected EnumSpec(DataType dataType, Optional<Integer> size, String[] values) {
    super(dataType, size, null);
    this.values = values;
  }

  public static EnumSpec make(DataType type, int size, String[] values) {
    return new EnumSpec(type, Optional.of(size), values);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof EnumSpec)) return false;
    if (!super.equals(o)) return false;
    EnumSpec enumSpec = (EnumSpec) o;
    return Arrays.equals(values, enumSpec.values);
  }

  @Override
  public int hashCode() {
    int result = super.hashCode();
    result = 31 * result + Arrays.hashCode(values);
    return result;
  }

  @Override
  public String toString() {
    return "EnumSpec{" +
      "dataType=" + dataType +
      ", size=" + size +
      ", preciseScale=" + Arrays.toString(preciseScale) +
      ", values=" + Arrays.toString(values) +
      '}';
  }

}
