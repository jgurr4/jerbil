package com.ple.jerbil.data.selectExpression;

import com.ple.jerbil.data.DataSpec;
import com.ple.jerbil.data.DataType;
import com.ple.jerbil.data.query.Table;
import com.ple.jerbil.data.selectExpression.NumericExpression.NumericColumn;
import com.ple.jerbil.data.selectExpression.booleanExpression.BooleanExpression;

/**
 * PartialColumn was made to allow column expressions to be created without requiring a DataSpec.
 * That way it can be defined later as part of our fluent api, rather than always being required before dataspec is defined.
 * For example Column.make('id', user).int().primary()
 * After Column.make() it's only a PartialColumn but after .int() it becomes a Column.
 */
public class PartialColumn extends OrderedExpression implements Expression, Column {

  public final String name;
  public final Table table;
  public final boolean indexed;
  public final boolean primary;

  protected PartialColumn(String name, Table table, boolean indexed, boolean primary) {
    this.name = name;
    this.table = table;
    this.indexed = indexed;
    this.primary = primary;
  }

  public static PartialColumn make(String name, Table table) {
    return new PartialColumn(name, table, false, false);
  }

  public static PartialColumn make(String name, Table table, boolean indexed, boolean primary) {
    return new PartialColumn(name, table, indexed, primary);
  }

  @Override
  public BooleanExpression eq(Expression item) {
    return null;
  }

  public NumericColumn asInt() {
    return NumericColumn.make(this.name, this.table);
  }

  public StringColumn asVarchar() {
    return StringColumn.make(this.name, this.table);
  }

  public StringColumn asVarchar(int size) {
    return StringColumn.make(this.name, this.table, size);
  }

  public StringColumn asEnum(Object enumObj) {
    return StringColumn.make(this.name, this.table, DataSpec.make(DataType.enumeration, enumObj), this.indexed, this.primary);
  }

  public NumericColumn primary() {
    return NumericColumn.make(this.name, this.table, true);
  }

  public PartialColumn indexed() {
    return PartialColumn.make(this.name, this.table, true, this.primary);
  }

  public NumericColumn id() {
    return NumericColumn.make(this.name, this.table, true);
  }

  public String getName() {
    return name;
  }

  @Override
  public Table getTable() {
    return this.getTable();
  }

}
