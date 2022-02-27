package com.ple.jerbil.data.selectExpression;

import com.ple.jerbil.data.*;
import com.ple.jerbil.data.query.Table;
import com.ple.jerbil.data.selectExpression.booleanExpression.BooleanExpression;
import org.jetbrains.annotations.Nullable;

import java.time.LocalDateTime;

@Immutable
public class DateColumn extends Column<DateColumn> implements DateExpression {

  protected DateColumn(String columnName, Table table, DataSpec dataSpec, Expression generatedFrom,
                       @Nullable DateExpression defaultValue, @Nullable DateExpression onUpdate,
                       BuildingHints hints) {
    super(columnName, table, dataSpec, generatedFrom, defaultValue, onUpdate, hints);
  }

  @Override
  public DateColumn make(String columnName, DataSpec dataSpec, Expression generatedFrom) {
    return new DateColumn(columnName, table, dataSpec, generatedFrom, (DateExpression) defaultValue,
        (DateExpression) onUpdate, BuildingHints.make(0b0));
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

  @Override
  public DateColumn onUpdate(Expression onUpdate) {
    return new DateColumn(columnName, table, dataSpec, generatedFrom, (DateExpression) defaultValue,
        (DateExpression) onUpdate, hints);
  }

  @Override
  public DateColumn onUpdate(Enum<?> value) {
    return null;
  }

  public static DateColumn make(String columnName, Table table, DataSpec dataSpec, BuildingHints hints) {
    return new DateColumn(columnName, table, dataSpec, null, null, null, hints);
  }

  public static DateColumn make(String columnName, Table table, DataSpec dataSpec) {
    return new DateColumn(columnName, table, dataSpec, null, null, null, BuildingHints.make(0b0));
  }

  public static DateColumn make(String columnName, Table table) {
    return new DateColumn(columnName, table, DataSpec.make(DataType.datetime), null, null, null, BuildingHints.make(0b0));
  }

  public DateColumn defaultValue(DateExpression dateExp) {
    return new DateColumn(columnName, table, dataSpec, generatedFrom, dateExp, (DateExpression) onUpdate, hints);
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
    return new DateColumn(columnName, table, dataSpec, generatedFrom, LiteralDate.make(ldateTime),
        (DateExpression) onUpdate, hints);
  }
}
