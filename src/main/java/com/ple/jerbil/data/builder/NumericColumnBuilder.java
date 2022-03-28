package com.ple.jerbil.data.builder;

import com.ple.jerbil.data.query.Table;
import com.ple.jerbil.data.selectExpression.Expression;
import com.ple.jerbil.data.selectExpression.LiteralNull;
import com.ple.jerbil.data.selectExpression.NumericExpression.NumericColumn;
import com.ple.jerbil.data.selectExpression.NumericExpression.NumericExpression;

public class NumericColumnBuilder extends ColumnBuilder implements ColumnBuilderServices{
  public NumericColumn column;

  protected NumericColumnBuilder(DatabaseBuilder dbBuild, String columnName, Table table, NumericColumn column) {
    super(dbBuild, tblBuild, columnName, table);
    this.column = column;
  }

  public static NumericColumnBuilder make(DatabaseBuilder dbBuild, NumericColumn column) {
    return new NumericColumnBuilder(dbBuild, column.columnName, column.table, column);
  }

  public NumericColumnBuilder ai() {
    column = NumericColumn.make(column.columnName, column.table, column.dataSpec, column.hints.autoInc().primary());
    return this;
  }

  public NumericColumnBuilder unsigned() {
    column = NumericColumn.make(column.columnName, column.table, column.dataSpec, column.hints.unsigned());
    return this;
  }

  public NumericColumnBuilder invisible() {
    return null;
  }

  @Override
  public ColumnBuilder allowNull() {
    return null;
  }

  @Override
  public ColumnBuilder defaultValue(Expression e) {
    return null;
  }

  @Override
  public ColumnBuilder defaultValue(Enum<?> value) {
    return null;
  }

  @Override
  public ColumnBuilder indexed() {
    return null;
  }

  public NumericColumnBuilder primary() {
    return null;
  }

  @Override
  public ColumnBuilder unique() {
    return null;
  }

  @Override
  public NumericColumn indexed() {
    return new NumericColumn(columnName, table, dataSpec, (NumericExpression) defaultValue, hints.index());
  }

  @Override
  public NumericColumn primary() {
    return new NumericColumn(columnName, table, dataSpec, null, hints.primary());
  }

  @Override
  public NumericColumn unique() {
    return null;
  }

  @Override
  public NumericColumn invisible() {
    Expression newDefault = defaultValue;
    if (defaultValue == null) {
      newDefault = LiteralNull.instance;
    }
    return new NumericColumn(columnName, table, dataSpec, (NumericExpression) newDefault, hints.invisible());
  }

  @Override
  public NumericColumn allowNull() {
    return null;
  }

  @Override
  public NumericColumn defaultValue(Expression e) {
    return null;
  }

  @Override
  public NumericColumn defaultValue(Enum<?> value) {
    return null;
  }

  public NumericColumn ai() {
    return new NumericColumn(columnName, table, dataSpec, null, hints.autoInc().primary());
  }

  public NumericColumn unsigned() {
    return new NumericColumn(columnName, table, dataSpec, (NumericExpression) defaultValue, hints.unsigned());
  }

}
