package com.ple.jerbil.sql.query;

import com.ple.jerbil.sql.fromExpression.Table;
import org.jetbrains.annotations.Nullable;

public class PartialQuery extends Query {


  protected PartialQuery(@Nullable Table table) {
    super(table);
  }

}
