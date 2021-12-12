package com.ple.util;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

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
    V[] result = Arrays.copyOf(values, values.length + 1);
    result[values.length] = v;
    return IArrayList.make(result);
  }

  @NotNull
  @Override
  public Iterator<V> iterator() {
    return new Iterator<>() {
      private int currentIndex = 0;

      @Override
      public boolean hasNext() {
        return currentIndex < values.length && values[currentIndex] != null;
      }

      @Override
      public V next() {
        return values[currentIndex++];
      }
    };
  }

}
