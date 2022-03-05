package com.ple.jerbil.data.selectExpression;

import com.ple.jerbil.data.*;
import com.ple.jerbil.data.GenericInterfaces.Immutable;
import com.ple.jerbil.data.query.Table;
import com.ple.jerbil.data.selectExpression.booleanExpression.BooleanExpression;
import org.jetbrains.annotations.Nullable;

import java.time.LocalDateTime;

@Immutable
public class DateColumn extends Column<DateColumn> implements DateExpression {

  protected DateColumn(String columnName, Table table, DataSpec dataSpec, @Nullable DateExpression defaultValue, BuildingHints hints) {
    super(columnName, table, dataSpec, defaultValue, hints);
  }

  @Override
  public DateColumn make(String columnName, DataSpec dataSpec) {
    return new DateColumn(columnName, table, dataSpec, (DateExpression) defaultValue, BuildingHints.make());
  }

  public static DateColumn make(String columnName, Table table, DataSpec dataSpec, DateExpression defaultValue, BuildingHints hints) {
    return new DateColumn(columnName, table, dataSpec, defaultValue, hints);
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

  public static DateColumn make(String columnName, Table table, DataSpec dataSpec, BuildingHints hints) {
    return new DateColumn(columnName, table, dataSpec, null, hints);
  }

  public static DateColumn make(String columnName, Table table, DataSpec dataSpec) {
    return new DateColumn(columnName, table, dataSpec, null, BuildingHints.make());
  }

  public static DateColumn make(String columnName, Table table) {
    return new DateColumn(columnName, table, DataSpec.make(DataType.datetime),null, BuildingHints.make());
  }

  public DateColumn defaultValue(DateExpression dateExp) {
    return new DateColumn(columnName, table, dataSpec, dateExp, hints);
  }

/*
  @Override
  public DateExpression plus(DateInterval dateInterval) {
    return null;
  }

  @Override
  public DateExpression minus(DateInterval dateInterval) {
    return null;
  }

*/
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

  public DateColumn defaultValue(LocalDateTime ldateTime) {
    return new DateColumn(columnName, table, dataSpec, LiteralDate.make(ldateTime), hints);
  }
}
