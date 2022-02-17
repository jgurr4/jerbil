package com.ple.jerbil.data.selectExpression;

import com.ple.jerbil.data.DataSpec;
import com.ple.jerbil.data.DataType;
import com.ple.jerbil.data.Immutable;
import com.ple.jerbil.data.query.Table;
import com.ple.jerbil.data.selectExpression.NumericExpression.NumericColumn;
import com.ple.jerbil.data.selectExpression.booleanExpression.BooleanExpression;

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
  public final boolean indexed;
  public final boolean primary;

  protected PartialColumn(String columnName, Table table, boolean indexed, boolean primary) {
    this.columnName = columnName;
    this.table = table;
    this.indexed = indexed;
    this.primary = primary;
  }

  public static PartialColumn make(String name, Table table) {
    return new PartialColumn(name, table, false, false);
  }

  @Override
  public BooleanExpression isGreaterThan(Expression i) {
    return null;
  }

  @Override
  public BooleanExpression isLessThan(Expression i) {
    return null;
  }

  @Override
  public BooleanExpression eq(Expression item) {
    return null;
  }

  public NumericColumn asInt() {
    return NumericColumn.make(columnName, DataSpec.make(DataType.integer));
  }

  public StringColumn asVarchar() {
    return StringColumn.make(columnName, DataSpec.make(DataType.varchar, 255));
  }

  public StringColumn asVarchar(int size) {
    return StringColumn.make(columnName, size);
  }

  public NumericColumn asBigInt() {
    return NumericColumn.make(columnName, DataSpec.make(DataType.bigint));
  }

  public NumericColumn asDecimal(int precision, int scale) {
    return NumericColumn.make(columnName, DataSpec.make(DataType.decimal, precision, scale));
  }

  public StringColumn asEnum(Class enumObj) {
    return StringColumn.make(columnName, DataSpec.make(DataType.enumeration, enumObj), indexed);
  }
  public NumericColumn bigId() {
    return NumericColumn.make(columnName, DataSpec.make(DataType.bigint), false, true, true);
  }

  public NumericColumn id() {
    return NumericColumn.make(columnName, DataSpec.make(DataType.integer), false, true, true);
  }

  public boolean isPrimary() {
    return primary;
  }

  public boolean isIndexed() {
    return indexed;
  }

  public String getColumnName() {
    return columnName;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof PartialColumn)) return false;
    PartialColumn that = (PartialColumn) o;
    return isIndexed() == that.isIndexed() && isPrimary() == that.isPrimary() && getColumnName().equals(that.getColumnName());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getColumnName(), isIndexed(), isPrimary());
  }

  @Override
  public String toString() {
    return "PartialColumn{" +
      "name='" + columnName + '\'' +
      ", indexed=" + indexed +
      ", primary=" + primary +
      '}';
  }

}
