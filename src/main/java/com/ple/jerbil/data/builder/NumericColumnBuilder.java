package com.ple.jerbil.data.builder;

import com.ple.jerbil.data.BuildingHints;
import com.ple.jerbil.data.query.Table;
import com.ple.jerbil.data.selectExpression.Expression;
import com.ple.jerbil.data.selectExpression.LiteralNull;
import com.ple.jerbil.data.selectExpression.NumericExpression.NumericColumn;
import com.ple.jerbil.data.selectExpression.NumericExpression.NumericExpression;

public class NumericColumnBuilder extends ColumnBuilder implements ColumnBuilderServices{
  private NumericColumn column;
  private BuildingHints hints;

  protected NumericColumnBuilder(DatabaseBuilder dbBuild, TableBuilder tblBuild, String columnName, Table table, NumericColumn column,
                                 BuildingHints hints) {
    super(dbBuild, tblBuild, columnName, table);
    this.column = column;
    this.hints = hints;
  }

  public static NumericColumnBuilder make(DatabaseBuilder dbBuild, TableBuilder tblBuild, NumericColumn column) {
    return new NumericColumnBuilder(dbBuild, tblBuild, column.columnName, column.table, column, BuildingHints.empty);
  }

  public static NumericColumnBuilder make(DatabaseBuilder dbBuild, TableBuilder tblBuild, NumericColumn column, BuildingHints hints) {
    return new NumericColumnBuilder(dbBuild, tblBuild, column.columnName, column.table, column, hints);
  }

  public NumericColumnBuilder ai() {
    column = NumericColumn.make(column.columnName, column.table, column.dataSpec, column.props.autoInc());
    hints = hints.primary().autoInc();
    return this;
  }

  public NumericColumnBuilder unsigned() {
    column = NumericColumn.make(column.columnName, column.table, column.dataSpec, column.props.unsigned());
    hints = hints.unsigned();
    return this;
  }

  public NumericColumnBuilder invisible() {
    column = NumericColumn.make(column.columnName, column.table, column.dataSpec, column.props.invisible());
    hints = hints.invisible();
    return this;
  }

  public NumericColumnBuilder allowNull() {
    column = NumericColumn.make(column.columnName, column.table, column.dataSpec, column.props.allowNull());
    hints = hints.allowNull();
    return this;
  }

  public NumericColumnBuilder defaultValue(Expression defaultValue) {
    column = NumericColumn.make(column.columnName, column.table, column.dataSpec, (NumericExpression) defaultValue, column.props);
    return this;
  }

  @Override
  public ColumnBuilder defaultValue(Enum<?> value) {
    return null;
  }

  public NumericColumnBuilder indexed() {
    hints = hints.index();
    return this;
  }

  public NumericColumnBuilder primary() {
    hints = hints.primary();
    return this;
  }

  public NumericColumnBuilder unique() {
    hints = hints.unique();
    return this;
  }
}
