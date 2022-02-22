package com.ple.jerbil.data.selectExpression.NumericExpression;

import com.ple.jerbil.data.BuildingHints;
import com.ple.jerbil.data.DataSpec;
import com.ple.jerbil.data.DataType;
import com.ple.jerbil.data.Immutable;
import com.ple.jerbil.data.query.Table;
import com.ple.jerbil.data.selectExpression.Column;
import com.ple.jerbil.data.selectExpression.Expression;
import com.ple.jerbil.data.selectExpression.booleanExpression.BooleanExpression;
import com.ple.jerbil.data.selectExpression.booleanExpression.Equals;
import com.ple.jerbil.data.selectExpression.booleanExpression.GreaterThan;
import org.jetbrains.annotations.Nullable;

@Immutable
public class NumericColumn extends Column<NumericColumn> implements NumericExpression {

  protected NumericColumn(String columnName, Table table, DataSpec dataSpec, @Nullable Expression generatedFrom,
                          @Nullable NumericExpression defaultValue, @Nullable NumericExpression onUpdate,
                          BuildingHints hints) {
    super(columnName, table, dataSpec, generatedFrom, defaultValue, onUpdate, hints);
  }

  public static NumericColumn make(String columnName, Table table, DataSpec dataSpec, BuildingHints hints) {
    return new NumericColumn(columnName, table, dataSpec, null, null, null, hints);
  }

  @Override
  public NumericColumn make(String columnName, DataSpec dataSpec, Expression generatedFrom) {
    return new NumericColumn(columnName, table, dataSpec, generatedFrom, (NumericExpression) defaultValue,
        (NumericExpression) onUpdate, hints);
  }

  public static NumericColumn make(String columnName, Table table, BuildingHints hints) {
    return new NumericColumn(columnName, table, DataSpec.make(DataType.integer), null, null,
        null, hints);
  }

  public static NumericColumn make(String columnName, Table table, DataSpec dataSpec, NumericExpression generatedFrom,
                                   NumericExpression defaultValue, NumericExpression onUpdate, BuildingHints hints) {
    return new NumericColumn(columnName, table, dataSpec, generatedFrom, defaultValue, onUpdate, hints);
  }


  @Override
  public NumericColumn indexed() {
    return new NumericColumn(columnName, table, dataSpec, generatedFrom, (NumericExpression) defaultValue,
        (NumericExpression) onUpdate, BuildingHints.make(0b0100000000000000 + hints.flags));
  }

  @Override
  public NumericColumn primary() {
    return new NumericColumn(columnName, table, dataSpec, generatedFrom, (NumericExpression) defaultValue,
        (NumericExpression) onUpdate, BuildingHints.make(0b1000000000000000 + hints.flags));
  }

  @Override
  public NumericColumn unique() {
    return null;
  }

  @Override
  public NumericColumn invisible() {
    return new NumericColumn(columnName, table, dataSpec, generatedFrom, (NumericExpression) defaultValue,
        (NumericExpression) onUpdate, BuildingHints.make(0b0000100000000000 + hints.flags));
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

  @Override
  public NumericColumn onUpdate(Expression e) {
    return null;
  }

  @Override
  public NumericColumn onUpdate(Enum<?> value) {
    return null;
  }

  public static NumericColumn make(String columnName, Table table, DataSpec dataSpec, NumericExpression generatedFrom,
                                   NumericExpression defaultValue, NumericExpression onUpdate) {
    return new NumericColumn(columnName, table, dataSpec, generatedFrom, defaultValue, onUpdate, BuildingHints.make(0b0));
  }

  public static NumericColumn make(String columnName, Table table, DataSpec dataSpec) {
    return new NumericColumn(columnName, table, dataSpec, null, null, null, BuildingHints.make(0b0));
  }

  public static NumericColumn make(String columnName, Table table, int size) {
    return new NumericColumn(columnName, table, DataSpec.make(DataType.integer, size), null, null,
        null, BuildingHints.make(0b0));
  }

  public static NumericColumn make(String columnName, Table table) {
    return new NumericColumn(columnName, table, DataSpec.make(DataType.integer), null, null,
        null, BuildingHints.make(0b0));
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
        "dataSpec=" + dataSpec +
        ", generatedFrom=" + generatedFrom +
        ", defaultValue=" + defaultValue +
        ", columnName='" + columnName + '\'' +
        ", table=" + table +
        '}';
  }

  public NumericColumn ai() {
    return new NumericColumn(columnName, table, dataSpec, generatedFrom, (NumericExpression) defaultValue,
        (NumericExpression) onUpdate, BuildingHints.make(0b1000000100000000 + hints.flags));
  }

  public NumericColumn unsigned() {
    return new NumericColumn(columnName, table, dataSpec, generatedFrom, (NumericExpression) defaultValue,
        (NumericExpression) onUpdate, BuildingHints.make(0b0000001000000000 + hints.flags));
  }
}
