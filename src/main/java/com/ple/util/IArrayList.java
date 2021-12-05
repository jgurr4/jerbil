package com.ple.util;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

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

  @Override
  public IList<V> addAll(IList<V> list) {
    V[] result = Arrays.copyOf(this.values, this.values.length + list.toArray().length);
    for (int i = 0; i < list.toArray().length; i++) {
      result[result.length - list.toArray().length + i] = list.toArray()[i];
    }
    return IArrayList.make(result);
  }

  @Override
  public IList<V> add(V v) {
    V[] result = Arrays.copyOf(this.values, this.values.length + 1);
    result[result.length - 1] = v;
    return IArrayList.make(result);
  }

}
