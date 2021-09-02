package com.ple.jerbil.sql.query;

import com.ple.jerbil.sql.fromExpression.Table;
import org.jetbrains.annotations.Nullable;

public class UpdateQuery extends CompleteQuery {

  protected UpdateQuery(@Nullable Table table) {
    super(table, null, null, null);
  }

}
