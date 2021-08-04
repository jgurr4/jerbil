package com.ple.jerbil.sql.query;

import com.ple.jerbil.sql.fromExpression.Table;
import org.jetbrains.annotations.Nullable;

public class PartialUpdateQuery extends PartialQuery {

  protected PartialUpdateQuery(@Nullable Table table) {
    super(table);
  }

}
