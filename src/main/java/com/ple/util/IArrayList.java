package com.ple.util;

public class IArrayList<V> implements IList<V> {
  public final V[] values;

  private IArrayList(V[] values) {
    this.values = values;
  }

  public static <V> IArrayList<V> make(V... values) {
    return new IArrayList<>(values);
  }

  @Override
  public V[] toArray() {
    return this.values;
  }
}
