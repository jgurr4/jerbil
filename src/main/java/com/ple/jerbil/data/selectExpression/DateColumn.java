package com.ple.jerbil.data.selectExpression;

import com.ple.jerbil.data.*;
import com.ple.jerbil.data.query.Table;
import com.ple.jerbil.data.selectExpression.booleanExpression.BooleanExpression;

@Immutable
public class DateColumn extends Column<DateColumn> implements DateExpression {

  protected DateColumn(String name, Table table, DataSpec dataSpec, Expression generatedFrom,
                       DateExpression defaultValue,
                       BuildingHints hints) {
    super(name, table, dataSpec, generatedFrom, defaultValue, hints);
  }

  @Override
  public DateColumn make(String name, DataSpec dataSpec, Expression generatedFrom) {
    return new DateColumn(name, table, dataSpec, generatedFrom, (DateExpression) defaultValue,
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

  public static DateColumn make(String name, Table table, DataSpec dataSpec, BuildingHints hints) {
    return new DateColumn(name, table, dataSpec, null, null, hints);
  }

  public static DateColumn make(String name, Table table, DataSpec dataSpec) {
    return new DateColumn(name, table, dataSpec, null, null, BuildingHints.make(0b00000000));
  }

  public static DateColumn make(String name, Table table) {
    return new DateColumn(name, table, DataSpec.make(DataType.datetime), null, null, BuildingHints.make(0b00000000));
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

}
