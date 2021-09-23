package com.ple.jerbil.sql.selectExpression;

import com.ple.jerbil.sql.DataSpec;
import com.ple.jerbil.sql.DelayedImmutable;
import com.ple.jerbil.sql.fromExpression.Table;
import org.jetbrains.annotations.Nullable;

/**
 *
 */
@DelayedImmutable
public interface Column {

  public static PartialColumn make(String name, Table table) {
    return new PartialColumn(name, table, null, false, false);
  }

}
