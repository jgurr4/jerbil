package com.ple.jerbil.sql.expression;

import com.ple.jerbil.sql.DataSpec;
import com.ple.jerbil.sql.DataType;
import com.ple.jerbil.sql.DelayedImmutable;
import com.ple.jerbil.sql.fromExpression.Table;
import org.jetbrains.annotations.Nullable;

@DelayedImmutable
public class Column extends PartialColumn {

  public Column(String name, Table table, @Nullable DataSpec dataSpec, boolean indexed, boolean primary) {
    super(name, table, dataSpec, indexed, primary);
  }

  public static PartialColumn make(String name, Table table) {
    return new PartialColumn(name, table, null, false, false);
  }

  public Column primary() {
    return new Column(this.name, this.table, this.dataSpec, this.indexed, true);
  }

}
