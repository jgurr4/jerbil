package com.ple.jerbil.data.selectExpression.NumericExpression;

import com.ple.jerbil.data.DataSpec;
import com.ple.jerbil.data.DataType;
import com.ple.jerbil.data.Immutable;
import com.ple.jerbil.data.selectExpression.Column;
import com.ple.jerbil.data.selectExpression.Expression;
import com.ple.jerbil.data.selectExpression.booleanExpression.BooleanExpression;
import com.ple.jerbil.data.selectExpression.booleanExpression.Equals;
import com.ple.jerbil.data.selectExpression.booleanExpression.GreaterThan;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

@Immutable
public class NumericColumn extends Column<NumericColumn> implements NumericExpression {

  public final boolean autoIncrement;

  protected NumericColumn(String name, DataSpec dataSpec, boolean indexed, boolean primary, boolean autoIncrement, @Nullable Expression generatedFrom, @Nullable NumericExpression defaultValue) {
    super(name, dataSpec, indexed, primary, generatedFrom, defaultValue);
    this.autoIncrement = autoIncrement;
  }

  @Override
  public NumericColumn make(String name, DataSpec dataSpec, boolean indexed, boolean primary, Expression generatedFrom) {
    return new NumericColumn(name, dataSpec, indexed, primary, autoIncrement, generatedFrom, null);
  }

  public static NumericColumn make(String name, DataSpec dataSpec, boolean indexed, boolean primary, boolean autoIncrement, NumericExpression generatedFrom, NumericExpression defaultValue) {
    return new NumericColumn(name, dataSpec, indexed, primary, autoIncrement, generatedFrom, defaultValue);
  }

  public static NumericColumn make(String name, DataSpec dataSpec, boolean indexed, boolean primary, boolean autoIncrement) {
    return new NumericColumn(name, dataSpec, indexed, primary, autoIncrement, null, null);
  }

  public static NumericColumn make(String name, int size) {
    return new NumericColumn(name, DataSpec.make(DataType.integer, size), false, false, false, null, null);
  }

  public static NumericColumn make(String name, boolean primary) {
    return new NumericColumn(name, DataSpec.make(DataType.integer), false, primary, false, null, null);
  }

  public static NumericColumn make(String name, Boolean indexed, Boolean primary) {
    return new NumericColumn(name, DataSpec.make(DataType.integer), indexed, primary, false, null, null);
  }

  public static NumericColumn make(String name, DataSpec dataSpec, Boolean indexed, Boolean primary) {
    return new NumericColumn(name, dataSpec, indexed, primary, false, null, null);
  }

  public static NumericColumn make(String name, DataSpec dataSpec) {
    return new NumericColumn(name, dataSpec, false, false, false, null, null);
  }

  public boolean isAutoIncrement() {
    return autoIncrement;
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
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof NumericColumn)) return false;
    if (!super.equals(o)) return false;
    NumericColumn that = (NumericColumn) o;
    return isAutoIncrement() == that.isAutoIncrement();
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), isAutoIncrement());
  }

  @Override
  public String toString() {
    return "NumericColumn{" +
      "dataSpec=" + dataSpec +
      ", generatedFrom=" + generatedFrom +
      ", defaultValue=" + defaultValue +
      ", autoIncrement=" + autoIncrement +
      ", name='" + name + '\'' +
      ", indexed=" + indexed +
      ", primary=" + primary +
      '}';
  }

}
