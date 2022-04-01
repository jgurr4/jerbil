package com.ple.jerbil.data.selectExpression;

import com.ple.jerbil.data.ColumnProps;
import com.ple.jerbil.data.DataSpec;
import com.ple.jerbil.data.query.SelectQuery;
import com.ple.jerbil.data.query.Table;
import org.jetbrains.annotations.Nullable;

public class EnumeralColumn extends Column<EnumeralColumn> implements StringExpression {

  protected EnumeralColumn(String columnName, Table table, DataSpec dataSpec, @Nullable StringExpression defaultValue,
                           ColumnProps props) {
    super(columnName, table, dataSpec, defaultValue, props);
  }

  public static EnumeralColumn make(String columnName, Table table, DataSpec dataSpec) {
    return new EnumeralColumn(columnName, table, dataSpec, null, ColumnProps.make());
  }

  public static EnumeralColumn make(String columnName, Table table, DataSpec dataSpec, StringExpression defaultValue, ColumnProps props) {
    return new EnumeralColumn(columnName, table, dataSpec, defaultValue, props);
  }

  public static EnumeralColumn make(String columnName, Table table, DataSpec dataSpec, Enum<?> defaultValue) {
    return new EnumeralColumn(columnName, table, dataSpec, Literal.make(defaultValue.name()), ColumnProps.empty);
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
        ", props=" + props +
        ", table=" + table +
        '}';
  }
}
