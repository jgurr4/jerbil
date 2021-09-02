package com.ple.jerbil.sql.query;

import com.ple.jerbil.sql.fromExpression.Table;
import org.jetbrains.annotations.Nullable;

public class PartialQuery extends Query {

  public final Table table;

  protected PartialQuery(Table table) {
    this.table = table;
  }

}
