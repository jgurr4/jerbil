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

  public NumericColumn asInt() {
    return NumericColumn.make(columnName, table, DataSpec.make(DataType.integer));
  }

  public NumericColumn asInt(int size) {
    return NumericColumn.make(columnName, table, DataSpec.make(DataType.integer, size));
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
    return StringColumn.make(columnName, table, DataSpec.make(DataType.varchar, size));
  }

  public StringColumn asChar() {
    return StringColumn.make(columnName, table, DataSpec.make(DataType.character, 255));
  }

  public StringColumn asChar(int size) {
    return StringColumn.make(columnName, table, DataSpec.make(DataType.character, size));
  }

  public EnumeralColumn asEnum(Class<?> enumObj) {
    return EnumeralColumn.make(columnName, table, DataSpec.make(DataType.enumeration, enumObj));
  }

  public EnumeralColumn asSet(Class<?> enumObj) {
    return EnumeralColumn.make(columnName, table, DataSpec.make(DataType.set, enumObj));
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
    return NumericColumn.make(columnName, table, DataSpec.make(DataType.bigint), null,
        BuildingHints.make().primary().autoInc());
  }

  public NumericColumn mediumId() {
    return null;
  }

  public NumericColumn id() {
    return NumericColumn.make(columnName, table, BuildingHints.make().primary().autoInc());
  }

  public StringColumn asText() {
    return StringColumn.make(columnName, table, DataSpec.make(DataType.text));
  }

  public NumericColumn asIntUnsigned() {
    return NumericColumn.make(columnName, table, DataSpec.make(DataType.integer), BuildingHints.make().unsigned());
  }

  public NumericColumn asMediumIntUnsigned() {
    return NumericColumn.make(columnName, table, DataSpec.make(DataType.mediumint), BuildingHints.make().unsigned());
  }

  public NumericColumn asSmallInt() {
    return NumericColumn.make(columnName, table, DataSpec.make(DataType.smallint));
  }

  public NumericColumn asTinyInt() {
    return NumericColumn.make(columnName, table, DataSpec.make(DataType.tinyint));
  }

  public BooleanColumn asBoolean() {
    //MySql uses tinyint(1) data type to make a boolean column.
    return BooleanColumn.make(columnName, table, DataSpec.make(DataType.bool));
  }

  public NumericColumn asDouble() {
    return NumericColumn.make(columnName, table, DataSpec.make(DataType.aDouble));
  }

  public NumericColumn asFloat() {
    return NumericColumn.make(columnName, table, DataSpec.make(DataType.aFloat));
  }

  public DateColumn asDate() {
    return DateColumn.make(columnName, table, DataSpec.make(DataType.date));
  }

  public DateColumn asTime() {
    return DateColumn.make(columnName, table, DataSpec.make(DataType.time));
  }

  public DateColumn asDateTime() {
    return DateColumn.make(columnName, table, DataSpec.make(DataType.datetime));
  }

  public DateColumn asTimeStamp() {
    return DateColumn.make(columnName, table, DataSpec.make(DataType.timestamp));
  }

}
