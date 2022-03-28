package com.ple.jerbil.data.selectExpression;

import com.ple.jerbil.data.BuildingHints;
import com.ple.jerbil.data.DataSpec;
import com.ple.jerbil.data.query.SelectQuery;
import com.ple.jerbil.data.query.Table;
import org.jetbrains.annotations.Nullable;

public class EnumeralColumn extends Column<EnumeralColumn> implements StringExpression {

  protected EnumeralColumn(String columnName, Table table, DataSpec dataSpec, @Nullable StringExpression defaultValue,
                           BuildingHints hints) {
    super(columnName, table, dataSpec, defaultValue, hints);
  }

  public static EnumeralColumn make(String columnName, Table table, DataSpec dataSpec) {
    return new EnumeralColumn(columnName, table, dataSpec, null, BuildingHints.make());
  }

  public static EnumeralColumn make(String columnName, Table table, DataSpec dataSpec, StringExpression defaultValue, BuildingHints hints) {
    return new EnumeralColumn(columnName, table, dataSpec, defaultValue, hints);
  }

  @Override
  public EnumeralColumn make(String columnName, DataSpec dataSpec) {
    return null;
  }

  @Override
  public AliasedExpression as(String name) {
    return null;
  }

  @Override
  public SelectQuery select() {
    return null;
  }

  @Override
  public String toString() {
    return "EnumeralColumn{" +
        "columnName='" + columnName + '\'' +
        ", dataSpec=" + dataSpec +
        ", defaultValue=" + defaultValue +
        ", hints=" + hints +
        ", table=" + table +
        '}';
  }
}
