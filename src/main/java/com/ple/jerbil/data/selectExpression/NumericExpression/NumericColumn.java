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
                          @Nullable NumericExpression defaultValue, BuildingHints hints) {
    super(columnName, table, dataSpec, generatedFrom, defaultValue, hints);
  }

  public static NumericColumn make(String columnName, Table table, DataSpec dataSpec, NumericExpression generatedFrom,
                                   NumericExpression defaultValue, BuildingHints hints) {
    return new NumericColumn(columnName, table, dataSpec, generatedFrom, defaultValue, hints);
  }

  @Override
  public NumericColumn make(String columnName, DataSpec dataSpec, Expression generatedFrom) {
    return new NumericColumn(columnName, table, dataSpec, generatedFrom, (NumericExpression) defaultValue, hints);
  }

  @Override
  public NumericColumn indexed() {
    return null;
  }

  @Override
  public NumericColumn primary() {
    return null;
  }

  @Override
  public NumericColumn unique() {
    return null;
  }

  @Override
  public NumericColumn invisible() {
    return null;
  }

  public static NumericColumn make(String columnName, Table table, DataSpec dataSpec, NumericExpression generatedFrom,
                                   NumericExpression defaultValue) {
    return new NumericColumn(columnName, table, dataSpec, generatedFrom, defaultValue, BuildingHints.make(0b00000000));
  }

  public static NumericColumn make(String columnName, Table table, DataSpec dataSpec) {
    return new NumericColumn(columnName, table, dataSpec, null, null, BuildingHints.make(0b00000000));
  }

  public static NumericColumn make(String columnName, Table table, int size) {
    return new NumericColumn(columnName, table, DataSpec.make(DataType.integer, size), null, null,
        BuildingHints.make(0b00000000));
  }

  public static NumericColumn make(String columnName, Table table) {
    return new NumericColumn(columnName, table, DataSpec.make(DataType.integer), null, null,
        BuildingHints.make(0b00000000));
  }

  public GreaterThan isGreaterThan(Expression value) {
    return GreaterThan.make(this, value);
  }

  @Override
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
    //This has to be primary, it is implied.
    return null;
  }

  public NumericColumn unsigned() {
    return null;
  }
}
