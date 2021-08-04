package com.ple.jerbil.sql.query;

import com.ple.jerbil.sql.fromExpression.Table;
import org.jetbrains.annotations.Nullable;

public class PartialDeleteQuery extends PartialQuery {

  protected PartialDeleteQuery(@Nullable Table table) {
    super(table);
  }

}
