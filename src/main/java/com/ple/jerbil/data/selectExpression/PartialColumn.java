package com.ple.jerbil.data.selectExpression;

import com.ple.jerbil.data.BuildingHints;
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

  protected PartialColumn(String columnName, Table table) {
    this.columnName = columnName;
    this.table = table;
  }

  public static PartialColumn make(String name, Table table) {
    return new PartialColumn(name, table);
  }

/*
  public BooleanExpression isGreaterThan(Expression i) {
    return null;
  }

  public BooleanExpression isLessThan(Expression i) {
    return null;
  }

  public BooleanExpression eq(Expression item) {
    return null;
  }

  public BooleanExpression eq(Enum<?> value) {
    return null;
  }
*/
  public NumericColumn asInt() {
    return NumericColumn.make(columnName, table, DataSpec.make(DataType.integer));
  }

  public NumericColumn asInt(int i) {
    return null;
  }

  public NumericColumn asBigInt() {
    return NumericColumn.make(columnName, table, DataSpec.make(DataType.bigint));
  }

  public NumericColumn asDecimal(int precision, int scale) {
    return NumericColumn.make(columnName, table, DataSpec.make(DataType.decimal, precision, scale));
  }

  public StringColumn asVarchar() {
    return StringColumn.make(columnName, table, DataSpec.make(DataType.varchar, 255));
  }

  public StringColumn asVarchar(int size) {
    return StringColumn.make(columnName, table, size);
  }

  public EnumeralColumn asEnum(Class enumObj) {
    return EnumeralColumn.make(columnName, table, DataSpec.make(DataType.enumeration, enumObj));
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

  public NumericColumn bigId() {
    return NumericColumn.make(columnName, table, DataSpec.make(DataType.bigint), null, null,
        BuildingHints.make(0b10000001));
  }

  public NumericColumn mediumId() {
    return null;
  }

  public NumericColumn id() {
    return null;
  }

  public StringColumn asText() {
    return null;
  }

  public NumericColumn asIntUnsigned() {
    return null;
  }

  public NumericColumn asMediumIntUnsigned() {
    return null;
  }

  public NumericColumn asSmallInt() {
    return null;
  }

  public NumericColumn asTinyInt() {
    return null;
  }

  public BooleanColumn asBoolean() {
    //MySql uses tinyint(1) data type to make a boolean column.
    return null;
  }

  public NumericColumn asDouble() {
    return null;
  }

  public NumericColumn asFloat() {
    return null;
  }

  public EnumeralColumn asSet(Class<?> enumObj) {
    return null;
  }

  public DateColumn asDate() {
    return null;
  }

  public DateColumn asTime() {
    return null;
  }

  public DateColumn asDateTime() {
    return null;
  }

  public DateColumn asTimeStamp() {
    return null;
  }
}
