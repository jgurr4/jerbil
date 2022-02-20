package com.ple.jerbil.data.selectExpression;

import com.ple.jerbil.data.BuildingHints;
import com.ple.jerbil.data.DataSpec;
import com.ple.jerbil.data.DataType;
import com.ple.jerbil.data.query.Table;
import com.ple.jerbil.data.selectExpression.booleanExpression.BooleanExpression;
import com.ple.jerbil.data.selectExpression.booleanExpression.Equals;
import com.ple.jerbil.data.selectExpression.booleanExpression.GreaterThan;

public class StringColumn extends Column<StringColumn> implements StringExpression {

  protected StringColumn(String columnName, Table table, DataSpec dataSpec, Expression generatedFrom,
                         StringExpression defaultValue, BuildingHints hints) {
    super(columnName, table, dataSpec, generatedFrom, defaultValue, hints);
  }

  @Override
  public StringColumn make(String columnName, DataSpec dataSpec, Expression generatedFrom) {
    return new StringColumn(columnName, table, dataSpec, generatedFrom, (StringExpression) defaultValue, hints);
  }

  @Override
  public StringColumn indexed() {
    return new StringColumn(columnName, table, dataSpec, generatedFrom, (StringExpression) defaultValue, BuildingHints.make(0b01000000 + hints.flags));
  }

  @Override
  public StringColumn primary() {
    return null;
  }

  @Override
  public StringColumn unique() {
    return new StringColumn(columnName, table, dataSpec, generatedFrom, (StringExpression) defaultValue, BuildingHints.make(0b00000100 + hints.flags));
  }

  @Override
  public StringColumn invisible() {
    return null;
  }

  @Override
  public StringColumn allowNull() {
    //FIXME: Find out how I can include this with BuildingHints or make a new field.
    return null;
  }

  @Override
  public StringColumn defaultValue(Expression e) {
    return null;
  }

  @Override
  public StringColumn defaultValue(Enum<?> value) {
    return null;
  }

  @Override
  public StringColumn onUpdate(Expression e) {
    return null;
  }

  public static StringColumn make(String columnName, Table table, int size, Expression generatedFrom,
                                  StringExpression defaultValue, BuildingHints hints) {
    return new StringColumn(columnName, table, DataSpec.make(DataType.varchar, size), generatedFrom, defaultValue, hints);
  }

  public static StringColumn make(String columnName, Table table, int size, BuildingHints hints) {
    return new StringColumn(columnName, table, DataSpec.make(DataType.varchar, size), null, null, hints);
  }

  public static StringColumn make(String columnName, Table table, int size) {
    return new StringColumn(columnName, table, DataSpec.make(DataType.varchar, size), null, null,
        BuildingHints.make(0b00000000));
  }

  public static StringColumn make(String columnName, Table table) {
    return new StringColumn(columnName, table, DataSpec.make(DataType.varchar), null, null,
        BuildingHints.make(0b00000000));
  }

  public static StringColumn make(String columnName, Table table, DataSpec dataSpec) {
    return new StringColumn(columnName, table, dataSpec, null, null, BuildingHints.make(0b00000000));
  }

  public Equals eq(StringExpression value) {
    return Equals.make(this, value);
  }

  public Equals eq(String value) {
    return Equals.make(this, Literal.make(value));
  }

  public GreaterThan isGreaterThan(Expression value) {
    return GreaterThan.make(this, value);
  }

  public BooleanExpression isLessThan(Expression i) {
    return null;
  }

  public StringColumn defaultValue(StringExpression str) {
    return new StringColumn(columnName, table, dataSpec, generatedFrom, str, hints);
  }

  public StringColumn fullText() {
    return new StringColumn(columnName, table, dataSpec, generatedFrom, (StringExpression) defaultValue, BuildingHints.make(0b00010000 + hints.flags));
  }
}
