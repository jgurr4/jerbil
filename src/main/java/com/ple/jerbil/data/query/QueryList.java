package com.ple.jerbil.data.query;

import com.ple.jerbil.data.selectExpression.booleanExpression.BooleanExpression;
import com.ple.util.IArrayList;
import com.ple.util.IList;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Iterator;

/**
 * We need methods that CompleteQuery and IList is not going to have.
 * This will have method called .execute() which will execute a list of CompleteQuery and it will use .toSql() as needed.
 */
public class QueryList<T> implements IList<T> {

  public final T[] values;
  public final int size;

  protected QueryList(T[] values, int size) {
    this.values = values;
    this.size = size;
  }

  public static <T> QueryList<T> make(T... values) {
    return new QueryList(values, values.length);
  }

  @Override
  public T[] toArray() {
    return values;
  }

  @Override
  public IList<T> addAll(IList<T> list) {
    T[] result = Arrays.copyOf(this.values, this.values.length + list.toArray().length);
    for (int i = 0; i < list.toArray().length; i++) {
      result[result.length - list.toArray().length + i] = list.toArray()[i];
    }
    return QueryList.make(result);
  }

  @Override
  public IList<T> add(T t) {
    T[] result = Arrays.copyOf(this.values, this.values.length + 1);
    result[result.length - 1] = t;
    return QueryList.make(result);
  }

  @NotNull
  @Override
  public Iterator iterator() {
    return new Iterator<>() {
      private int currentIndex = 0;

      @Override
      public boolean hasNext() {
        return currentIndex < values.length && values[currentIndex] != null;
      }

      @Override
      public T next() {
        return values[currentIndex++];
      }
    };
  }

}
