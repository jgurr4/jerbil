package com.ple.jerbil.data.builder;

import com.ple.jerbil.data.query.Table;
import com.ple.jerbil.data.selectExpression.DateColumn;
import com.ple.jerbil.data.selectExpression.DateExpression;
import com.ple.jerbil.data.selectExpression.Expression;
import com.ple.jerbil.data.selectExpression.LiteralDate;

import java.time.LocalDateTime;

public class DateColumnBuilder extends ColumnBuilder {
  private DateColumn column;

  protected DateColumnBuilder(DatabaseBuilder dbBuild, TableBuilder tblBuild, String columnName, Table table, DateColumn column) {
    super(dbBuild, tblBuild, columnName, table);
    this.column = column;
  }

  public static DateColumnBuilder make(DatabaseBuilder dbBuild, TableBuilder tblBuild, DateColumn column) {
    return null;
  }

  @Override
  public DateColumn indexed() {
    return null;
  }

  @Override
  public DateColumn primary() {
    return null;
  }

  @Override
  public DateColumn unique() {
    return null;
  }

  @Override
  public DateColumn invisible() {
    return null;
  }

  @Override
  public DateColumn allowNull() {
    return null;
  }

  @Override
  public DateColumn defaultValue(Expression e) {
    return null;
  }

  @Override
  public DateColumn defaultValue(Enum<?> value) {
    return null;
  }

  public DateColumn onUpdate(Expression onUpdate) {
    return new DateColumn(columnName, table, dataSpec, (DateExpression) defaultValue, hints);
  }

  public DateColumn onUpdate(Enum<?> value) {
    return null;
  }

  public DateColumn onUpdateCurrentTimeStamp() {
    return new DateColumn(columnName, table, dataSpec, (DateExpression) defaultValue, hints.autoUpdateTime());
  }

  public DateColumn defaultValue(LocalDateTime ldateTime) {
    return new DateColumn(columnName, table, dataSpec, LiteralDate.make(ldateTime), hints);
  }
}
