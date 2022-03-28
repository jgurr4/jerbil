package com.ple.jerbil.data.builder;

import com.ple.jerbil.data.BuildingHints;
import com.ple.jerbil.data.DataSpec;
import com.ple.jerbil.data.DataType;
import com.ple.jerbil.data.query.Table;
import com.ple.jerbil.data.selectExpression.*;
import com.ple.jerbil.data.selectExpression.NumericExpression.NumericColumn;

public class PartialColumnBuilder extends ColumnBuilder {


  public PartialColumnBuilder(DatabaseBuilder dbBuild, TableBuilder tblBuild, String columnName, Table table) {
    super(dbBuild, tblBuild, columnName, table);
  }

  public static PartialColumnBuilder make(DatabaseBuilder dbBuild, TableBuilder tblBuild, String columnName, Table table) {
    return new PartialColumnBuilder(dbBuild, tblBuild, columnName, table);
  }

  public NumericColumnBuilder asBigInt() {
    return NumericColumnBuilder.make(getDbBuild(), PartialColumn.make(getColumnName(), getTable()).asBigInt());
  }

  public StringColumnBuilder asVarchar() {
    return null;
  }

  public StringColumnBuilder asVarchar(int size) {
    return null;
  }

  public StringColumnBuilder asText() {
    return null;
  }

  public NumericColumnBuilder asIntUnsigned() {
    return null;
  }

  public NumericColumnBuilder asInt() {
    return null;
  }

  public NumericColumnBuilder asInt(int i) {
    return null;
  }

  public NumericColumnBuilder asMediumIntUnsigned() {
    return null;
  }

  public NumericColumnBuilder asSmallInt() {
    return null;
  }

  public NumericColumnBuilder asDecimal(int i, int i2) {
    return null;
  }

  public NumericColumnBuilder asBoolean() {
    return null;
  }

  public NumericColumnBuilder asDouble() {
    return null;
  }

  public NumericColumnBuilder asFloat() {
    return null;
  }

  public EnumeralColumnBuilder asSet(Class<?> itemTypeClass) {
    return null;
  }

  public DateColumnBuilder asDate() {
    return null;
  }

  public EnumeralColumnBuilder asTime() {
    return null;
  }

  public DateColumnBuilder asDateTime() {
    return null;
  }

  public NumericColumnBuilder bigId() {
    return null;
  }

  public NumericColumnBuilder id() {
    return null;
  }

  public EnumeralColumnBuilder asEnum(Class<?> type) {
    return null;
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
