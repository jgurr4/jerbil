package com.ple.jerbil.data.selectExpression;

import com.ple.jerbil.data.BuildingHints;
import com.ple.jerbil.data.DataSpec;
import com.ple.jerbil.data.DataType;
import com.ple.util.Immutable;
import com.ple.jerbil.data.query.Table;
import com.ple.jerbil.data.selectExpression.NumericExpression.NumericColumn;

import java.util.Objects;

/**
 * PartialColumn was made to allow column expressions to be created without requiring a DataSpec.
 * That way it can be defined later as part of our fluent api, rather than always being required before dataspec is defined.
 * For example Column.make('id', user).int().primary()
 * After Column.make() it's only a PartialColumn but after .int() it becomes a Column.
 */
@Immutable
public class PartialColumn implements Expression, OrderedExpression {

  public final String columnName;
  public final Table table;

  protected PartialColumn(String columnName, Table table) {
    this.columnName = columnName;
    this.table = table;
  }

  public static PartialColumn make(String name, Table table) {
    return new PartialColumn(name, table);
  }

  public String getColumnName() {
    return columnName;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof PartialColumn)) return false;
    PartialColumn that = (PartialColumn) o;
    return getColumnName().equals(that.getColumnName()) && table.equals(that.table);
  }

  @Override
  public int hashCode() {
    return Objects.hash(getColumnName(), table);
  }

  @Override
  public String toString() {
    return "PartialColumn{" +
        "columnName='" + columnName + '\'' +
        ", table=" + table +
        '}';
  }

}
