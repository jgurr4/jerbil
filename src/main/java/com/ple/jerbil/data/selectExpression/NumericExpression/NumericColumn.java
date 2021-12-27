package com.ple.jerbil.data.selectExpression.NumericExpression;

import com.ple.jerbil.data.DataSpec;
import com.ple.jerbil.data.DataType;
import com.ple.jerbil.data.Immutable;
import com.ple.jerbil.data.query.Table;
import com.ple.jerbil.data.selectExpression.Column;
import com.ple.jerbil.data.selectExpression.Expression;
import com.ple.jerbil.data.selectExpression.booleanExpression.Equals;
import com.ple.jerbil.data.selectExpression.booleanExpression.GreaterThan;
import org.jetbrains.annotations.Nullable;

@Immutable
public class NumericColumn extends NumericExpression implements Column<NumericColumn> {

  public final String name;
  public final Table table;
  public final DataSpec dataSpec;
  public final boolean indexed;
  public final boolean primary;
  public final boolean autoIncrement;
  @Nullable public final NumericExpression generatedFrom;

  protected NumericColumn(String name, Table table, DataSpec dataSpec, boolean indexed, boolean primary, boolean autoIncrement, @Nullable NumericExpression generatedFrom) {
    this.name = name;
    this.table = table;
    this.dataSpec = dataSpec;
    this.indexed = indexed;
    this.primary = primary;
    this.autoIncrement = autoIncrement;
    this.generatedFrom = generatedFrom;
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

  public static NumericColumn make(String name, Table table) {
    return new NumericColumn(name, table, DataSpec.make(DataType.integer), false, false, false, null);
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

  @Override
  public String getName() {
    return this.name;
  }

  @Override
  public Table getTable() {
    return this.table;
  }

  @Override
  public NumericColumn primary() {
    return new NumericColumn(this.name, this.table, this.dataSpec, this.indexed, true, false, null);
  }

  @Override
  public NumericColumn indexed() {
    return new NumericColumn(this.name, this.table, this.dataSpec, true, this.primary, false, null);
  }

  public NumericColumn generatedFrom(NumericExpression generatedFrom) {
    return NumericColumn.make(name, table, dataSpec, indexed, primary, autoIncrement, generatedFrom);
  }

  @Override
  public boolean isPrimary() {
    return primary;
  }

  @Override
  public boolean isIndexed() {
    return indexed;
  }

  public boolean isAutoIncrement() {
    return autoIncrement;
  }

  public GreaterThan isGreaterThan(Expression value) {
    return GreaterThan.make(this, value);
  }

  public Equals eq(Expression value) {
    return Equals.make(this, value);
  }

}
