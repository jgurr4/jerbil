package com.ple.jerbil.data.selectExpression.NumericExpression;

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

import java.util.Objects;

@Immutable
public class NumericColumn extends Column<NumericColumn> implements NumericExpression {

  protected NumericColumn(String name, Table table, DataSpec dataSpec, @Nullable Expression generatedFrom, @Nullable NumericExpression defaultValue) {
    super(name, table, dataSpec, generatedFrom, defaultValue);
  }

  @Override
  public NumericColumn make(String name, DataSpec dataSpec, Expression generatedFrom) {
    return new NumericColumn(name, table, dataSpec, generatedFrom, (NumericExpression) defaultValue);
  }

  public static NumericColumn make(String name, Table table, DataSpec dataSpec, NumericExpression generatedFrom, NumericExpression defaultValue) {
    return new NumericColumn(name, table, dataSpec, generatedFrom, defaultValue);
  }

  public static NumericColumn make(String name, Table table, DataSpec dataSpec) {
    return new NumericColumn(name, table, dataSpec, null, null);
  }

  public static NumericColumn make(String name, Table table, int size) {
    return new NumericColumn(name, table, DataSpec.make(DataType.integer, size), null, null);
  }

  public static NumericColumn make(String name, Table table) {
    return new NumericColumn(name, table, DataSpec.make(DataType.integer), null, null);
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
}
