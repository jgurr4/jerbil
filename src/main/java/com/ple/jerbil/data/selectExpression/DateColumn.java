package com.ple.jerbil.data.selectExpression;

import com.ple.jerbil.data.*;
import com.ple.jerbil.data.query.Table;
import com.ple.jerbil.data.selectExpression.booleanExpression.BooleanExpression;

import java.time.LocalDateTime;

@Immutable
public class DateColumn extends Column<DateColumn> implements DateExpression {

  protected DateColumn(String columnName, Table table, DataSpec dataSpec, Expression generatedFrom,
                       DateExpression defaultValue,
                       BuildingHints hints) {
    super(columnName, table, dataSpec, generatedFrom, defaultValue, hints);
  }

  @Override
  public DateColumn make(String columnName, DataSpec dataSpec, Expression generatedFrom) {
    return new DateColumn(columnName, table, dataSpec, generatedFrom, (DateExpression) defaultValue,
        BuildingHints.make(0b00000000));
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
  public DateColumn defaultValue(Enum<?> value) {
    return null;
  }

  public static DateColumn make(String columnName, Table table, DataSpec dataSpec, BuildingHints hints) {
    return new DateColumn(columnName, table, dataSpec, null, null, hints);
  }

  public static DateColumn make(String columnName, Table table, DataSpec dataSpec) {
    return new DateColumn(columnName, table, dataSpec, null, null, BuildingHints.make(0b00000000));
  }

  public static DateColumn make(String columnName, Table table) {
    return new DateColumn(columnName, table, DataSpec.make(DataType.datetime), null, null, BuildingHints.make(0b00000000));
  }

  public DateColumn defaultValue(DateExpression dateExp) {
    return new DateColumn(columnName, table, dataSpec, generatedFrom, dateExp, hints);
  }

  @Override
  public DateExpression plus(DateInterval dateInterval) {
    return null;
  }

  @Override
  public DateExpression minus(DateInterval dateInterval) {
    return null;
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

  @Override
  public BooleanExpression eq(Enum<?> value) {
    return null;
  }

  public DateColumn defaultValue(LocalDateTime ldateTime) {
    return new DateColumn(columnName, table, dataSpec, generatedFrom, LiteralDate.make(ldateTime), hints);
  }
}
