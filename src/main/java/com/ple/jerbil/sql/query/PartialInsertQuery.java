package com.ple.jerbil.sql.query;

import com.ple.jerbil.sql.fromExpression.Table;

public class PartialInsertQuery extends PartialQueryWithValues {

  protected PartialInsertQuery(Table table) {
    super(table);
  }

  public static PartialInsertQuery make(Table table) {
    return new PartialInsertQuery(table);
  }

}
