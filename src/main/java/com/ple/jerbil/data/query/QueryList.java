package com.ple.jerbil.data.query;

import com.ple.util.IList;

/**
 * We need methods that CompleteQuery and IList is not going to have.
 * This will have method called .execute() which will execute a list of CompleteQuery and it will use .toSql() as needed.
 */
public class QueryList implements IList {

  @Override
  public Object[] toArray() {
    return new Object[0];
  }
}
