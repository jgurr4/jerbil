package com.ple.jerbil.data.selectExpression;

import com.ple.jerbil.data.*;
import com.ple.util.Immutable;
import com.ple.jerbil.data.query.Table;
import com.ple.jerbil.data.selectExpression.booleanExpression.BooleanExpression;
import org.jetbrains.annotations.Nullable;

import java.time.LocalDateTime;

@Immutable
public class DateColumn extends Column<DateColumn> implements DateExpression {

  protected DateColumn(String columnName, Table table, DataSpec dataSpec, @Nullable DateExpression defaultValue, ColumnProps props) {
    super(columnName, table, dataSpec, defaultValue, props);
  }

  @Override
  public DateColumn make(String columnName, DataSpec dataSpec) {
    return new DateColumn(columnName, table, dataSpec, (DateExpression) defaultValue, ColumnProps.make());
  }

  public static DateColumn make(String columnName, Table table, DataSpec dataSpec, DateExpression defaultValue, ColumnProps props) {
    return new DateColumn(columnName, table, dataSpec, defaultValue, props);
  }

  public static DateColumn make(String columnName, Table table, DataSpec dataSpec, ColumnProps props) {
    return new DateColumn(columnName, table, dataSpec, null, props);
  }

  public static DateColumn make(String columnName, Table table, DataSpec dataSpec) {
    return new DateColumn(columnName, table, dataSpec, null, ColumnProps.make());
  }

  public static DateColumn make(String columnName, Table table) {
    return new DateColumn(columnName, table, DataSpec.make(DataType.datetime),null, ColumnProps.make());
  }

  public DateColumn defaultValue(DateExpression dateExp) {
    return new DateColumn(columnName, table, dataSpec, dateExp, props);
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

  @Override
  public String toString() {
    return "DateColumn{" +
        "columnName='" + columnName + '\'' +
        ", dataSpec=" + dataSpec +
        ", defaultValue=" + defaultValue +
        ", props=" + props +
        ", table=" + table +
        '}';
  }

}
