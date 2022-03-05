package com.ple.jerbil.data.selectExpression.NumericExpression;

import com.ple.jerbil.data.BuildingHints;
import com.ple.jerbil.data.DataSpec;
import com.ple.jerbil.data.DataType;
import com.ple.jerbil.data.GenericInterfaces.Immutable;
import com.ple.jerbil.data.query.Table;
import com.ple.jerbil.data.selectExpression.Column;
import com.ple.jerbil.data.selectExpression.Expression;
import com.ple.jerbil.data.selectExpression.booleanExpression.BooleanExpression;
import com.ple.jerbil.data.selectExpression.booleanExpression.Equals;
import com.ple.jerbil.data.selectExpression.booleanExpression.GreaterThan;
import org.jetbrains.annotations.Nullable;

@Immutable
public class NumericColumn extends Column<NumericColumn> implements NumericExpression {

  protected NumericColumn(String columnName, Table table, DataSpec dataSpec, @Nullable NumericExpression defaultValue,
                          BuildingHints hints) {
    super(columnName, table, dataSpec, defaultValue, hints);
  }

  public static NumericColumn make(String columnName, Table table, DataSpec dataSpec, BuildingHints hints) {
    return new NumericColumn(columnName, table, dataSpec, null, hints);
  }

  @Override
  public NumericColumn make(String columnName, DataSpec dataSpec) {
    return new NumericColumn(columnName, table, dataSpec, (NumericExpression) defaultValue, hints);
  }

  public static NumericColumn make(String columnName, Table table, BuildingHints hints) {
    return new NumericColumn(columnName, table, DataSpec.make(DataType.integer),null, hints);
  }

  public static NumericColumn make(String columnName, Table table, DataSpec dataSpec, NumericExpression defaultValue,
                                   BuildingHints hints) {
    return new NumericColumn(columnName, table, dataSpec, defaultValue, hints);
  }


  @Override
  public NumericColumn indexed() {
    return new NumericColumn(columnName, table, dataSpec, (NumericExpression) defaultValue, hints.index());
  }

  @Override
  public NumericColumn primary() {
    return new NumericColumn(columnName, table, dataSpec, (NumericExpression) defaultValue, hints.primary());
  }

  @Override
  public NumericColumn unique() {
    return null;
  }

  @Override
  public NumericColumn invisible() {
    return new NumericColumn(columnName, table, dataSpec, (NumericExpression) defaultValue, hints.invisible());
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

  public static NumericColumn make(String columnName, Table table, DataSpec dataSpec, NumericExpression defaultValue)  {
    return new NumericColumn(columnName, table, dataSpec, defaultValue, BuildingHints.make());
  }

  public static NumericColumn make(String columnName, Table table, DataSpec dataSpec) {
    return new NumericColumn(columnName, table, dataSpec, null, BuildingHints.make());
  }

  public static NumericColumn make(String columnName, Table table, int size) {
    return new NumericColumn(columnName, table, DataSpec.make(DataType.integer, size), null, BuildingHints.make());
  }

  public static NumericColumn make(String columnName, Table table) {
    return new NumericColumn(columnName, table, DataSpec.make(DataType.integer), null, BuildingHints.make());
  }

  public GreaterThan isGreaterThan(Expression value) {
    return GreaterThan.make(this, value);
  }

  public BooleanExpression isLessThan(Expression i) {
    return null;
  }

  public Equals eq(Expression value) {
    return Equals.make(this, value);
  }

  @Override
  public String toString() {
    return "NumericColumn{" +
        "columnName='" + columnName +
        ", dataSpec=" + dataSpec +
//        ", generatedFrom=" + generatedFrom +
        ", defaultValue=" + defaultValue +
        ", table=" + table + '\'' +
        '}';
  }

  public NumericColumn ai() {
    return new NumericColumn(columnName, table, dataSpec, (NumericExpression) defaultValue, hints.autoInc().primary());
  }

  public NumericColumn unsigned() {
    return new NumericColumn(columnName, table, dataSpec, (NumericExpression) defaultValue, hints.unsigned());
  }
}
