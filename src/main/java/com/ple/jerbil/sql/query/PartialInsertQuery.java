package com.ple.jerbil.sql.query;

public class PartialInsertQuery extends PartialQueryWithValues {

  protected PartialInsertQuery(Table table) {
  }

  public static PartialInsertQuery make(Table table) {
    return new PartialInsertQuery(table);
  }

}
