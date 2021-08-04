package com.ple.jerbil.sql.query;

import com.ple.jerbil.sql.fromExpression.Table;
import org.jetbrains.annotations.Nullable;

public class PartialInsertQuery extends PartialQuery {

  protected PartialInsertQuery(@Nullable Table table) {
    super(table);
  }

}
