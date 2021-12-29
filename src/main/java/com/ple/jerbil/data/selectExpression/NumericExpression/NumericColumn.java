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

@Immutable
public class NumericColumn extends Column<NumericColumn> implements NumericExpression {

  public final boolean autoIncrement;
  @Nullable public final NumericExpression generatedFrom;

  protected NumericColumn(String name, Table table, DataSpec dataSpec, boolean indexed, boolean primary, boolean autoIncrement, @Nullable NumericExpression generatedFrom) {
    super(name, table, dataSpec, indexed, primary);
    this.autoIncrement = autoIncrement;
    this.generatedFrom = generatedFrom;
  }

  @Override
  public NumericColumn make(String name, Table table, DataSpec dataSpec, boolean indexed, boolean primary) {
    return new NumericColumn(name, table, dataSpec, indexed, primary, autoIncrement, generatedFrom);
  }

  public static NumericColumn make(String name, Table table, DataSpec dataSpec, boolean indexed, boolean primary, boolean autoIncrement, NumericExpression generatedFrom) {
    return new NumericColumn(name, table, dataSpec, indexed, primary, autoIncrement, generatedFrom);
  }

  public static NumericColumn make(String name, Table table, DataSpec dataSpec, boolean indexed, boolean primary, boolean autoIncrement) {
    return new NumericColumn(name, table, dataSpec, indexed, primary, autoIncrement, null);
  }

  public static NumericColumn make(String name, Table table, int size) {
    return new NumericColumn(name, table, DataSpec.make(DataType.integer, size), false, false, false, null);
  }

  public static NumericColumn make(String name, Table table, boolean primary) {
    return new NumericColumn(name, table, DataSpec.make(DataType.integer), false, primary, false, null);
  }

  public static NumericColumn make(String name, Table table, Boolean indexed, Boolean primary) {
    return new NumericColumn(name, table, DataSpec.make(DataType.integer), indexed, primary, false, null);
  }

  public static NumericColumn make(String name, Table table, DataSpec dataSpec, Boolean indexed, Boolean primary) {
    return new NumericColumn(name, table, dataSpec, indexed, primary, false, null);
  }

  public static NumericColumn make(String name, Table table, DataSpec dataSpec) {
    return new NumericColumn(name, table, dataSpec, false, false, false, null);
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

}
