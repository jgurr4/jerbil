package com.ple.jerbil.data.builder;

import com.ple.jerbil.data.*;
import com.ple.jerbil.data.query.Table;
import com.ple.jerbil.data.selectExpression.*;
import com.ple.jerbil.data.selectExpression.NumericExpression.NumericColumn;

public class PartialColumnBuilder extends ColumnBuilder {

  public PartialColumnBuilder(DatabaseBuilder dbBuild, TableBuilder tblBuild, String columnName, Table table) {
    super(dbBuild, tblBuild, columnName, table);
  }

  public static PartialColumnBuilder make(DatabaseBuilder dbBuild, TableBuilder tblBuild, String columnName,
                                          Table table) {
    return new PartialColumnBuilder(dbBuild, tblBuild, columnName, table);
  }

  public NumericColumnBuilder asBigInt() {
    return NumericColumnBuilder.make(getDbBuild(), getTblBuild(),
        NumericColumn.make(getColumnName(), getTable(), DataSpec.make(DataType.bigint)));
  }

  public StringColumnBuilder asVarchar() {
    return StringColumnBuilder.make(getDbBuild(), getTblBuild(),
        StringColumn.make(getColumnName(), getTable(), DataSpec.make(DataType.varchar)));
  }

  public StringColumnBuilder asVarchar(int size) {
    return StringColumnBuilder.make(getDbBuild(), getTblBuild(),
        StringColumn.make(getColumnName(), getTable(), DataSpec.make(DataType.varchar, size)));
  }

  public StringColumnBuilder asText() {
    return StringColumnBuilder.make(getDbBuild(), getTblBuild(),
        StringColumn.make(getColumnName(), getTable(), DataSpec.make(DataType.text)));
  }

  public NumericColumnBuilder asIntUnsigned() {
    return NumericColumnBuilder.make(getDbBuild(), getTblBuild(),
        NumericColumn.make(getColumnName(), getTable(), DataSpec.make(DataType.integer),
            ColumnProps.empty.unsigned()));
  }

  public NumericColumnBuilder asInt() {
    return NumericColumnBuilder.make(getDbBuild(), getTblBuild(),
        NumericColumn.make(getColumnName(), getTable(), DataSpec.make(DataType.integer)));
  }

  public NumericColumnBuilder asInt(int i) {
    return NumericColumnBuilder.make(getDbBuild(), getTblBuild(),
        NumericColumn.make(getColumnName(), getTable(), DataSpec.make(DataType.integer, i)));
  }

  public NumericColumnBuilder asMediumIntUnsigned() {
    return NumericColumnBuilder.make(getDbBuild(), getTblBuild(),
        NumericColumn.make(getColumnName(), getTable(), DataSpec.make(DataType.mediumint)));
  }

  public NumericColumnBuilder asSmallInt() {
    return NumericColumnBuilder.make(getDbBuild(), getTblBuild(),
        NumericColumn.make(getColumnName(), getTable(), DataSpec.make(DataType.smallint)));
  }

  public NumericColumnBuilder asDecimal(int i, int i2) {
    return NumericColumnBuilder.make(getDbBuild(), getTblBuild(),
        NumericColumn.make(getColumnName(), getTable(), DataSpec.make(DataType.smallint, i, i2)));
  }

  public BooleanColumnBuilder asBoolean() {
    return BooleanColumnBuilder.make(getDbBuild(), getTblBuild(),
        BooleanColumn.make(getColumnName(), getTable(), DataSpec.make(DataType.bool)));
  }

  public NumericColumnBuilder asDouble() {
    return NumericColumnBuilder.make(getDbBuild(), getTblBuild(),
        NumericColumn.make(getColumnName(), getTable(), DataSpec.make(DataType.aDouble)));
  }

  public NumericColumnBuilder asFloat() {
    return NumericColumnBuilder.make(getDbBuild(), getTblBuild(),
        NumericColumn.make(getColumnName(), getTable(), DataSpec.make(DataType.aFloat)));
  }

  public DateColumnBuilder asDate() {
    return DateColumnBuilder.make(getDbBuild(), getTblBuild(),
        DateColumn.make(getColumnName(), getTable(), DataSpec.make(DataType.date)));
  }

  public DateColumnBuilder asTime() {
    return DateColumnBuilder.make(getDbBuild(), getTblBuild(),
        DateColumn.make(getColumnName(), getTable(), DataSpec.make(DataType.time)));
  }

  public DateColumnBuilder asDateTime() {
    return DateColumnBuilder.make(getDbBuild(), getTblBuild(),
        DateColumn.make(getColumnName(), getTable(), DataSpec.make(DataType.datetime)));
  }

  public NumericColumnBuilder bigId() {
    return NumericColumnBuilder.make(getDbBuild(), getTblBuild(),
        NumericColumn.make(getColumnName(), getTable(), DataSpec.make(DataType.bigint), ColumnProps.make().unsigned().autoInc()),
        BuildingHints.make().primary().autoInc().unsigned());
  }

  public NumericColumnBuilder id() {
    return NumericColumnBuilder.make(getDbBuild(), getTblBuild(),
        NumericColumn.make(getColumnName(), getTable(), DataSpec.make(DataType.integer), ColumnProps.make().autoInc().unsigned()),
        BuildingHints.make().primary().autoInc().unsigned());
  }

  public EnumeralColumnBuilder asEnum(Class<?> type) {
    return EnumeralColumnBuilder.make(getDbBuild(), getTblBuild(), EnumeralColumn.make(getColumnName(), getTable(),
        EnumSpec.make(DataType.enumeration, type)));
  }

  public EnumeralColumnBuilder asSet(Class<?> type) {
    return EnumeralColumnBuilder.make(getDbBuild(), getTblBuild(), EnumeralColumn.make(getColumnName(), getTable(),
        EnumSpec.make(DataType.set, type)));
  }

}
