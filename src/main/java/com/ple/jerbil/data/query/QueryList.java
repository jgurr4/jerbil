package com.ple.jerbil.data.query;

import com.ple.jerbil.data.selectExpression.booleanExpression.BooleanExpression;
import com.ple.util.IList;

/**
 * We need methods that CompleteQuery and IList is not going to have.
 * This will have method called .execute() which will execute a list of CompleteQuery and it will use .toSql() as needed.
 */
public class QueryList<T> implements IList {

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
}
