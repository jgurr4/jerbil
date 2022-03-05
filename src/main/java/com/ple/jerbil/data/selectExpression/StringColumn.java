package com.ple.jerbil.data.selectExpression;

import com.ple.jerbil.data.BuildingHints;
import com.ple.jerbil.data.DataSpec;
import com.ple.jerbil.data.DataType;
import com.ple.jerbil.data.query.Table;
import com.ple.jerbil.data.selectExpression.booleanExpression.BooleanExpression;
import com.ple.jerbil.data.selectExpression.booleanExpression.Equals;
import com.ple.jerbil.data.selectExpression.booleanExpression.GreaterThan;
import org.jetbrains.annotations.Nullable;

public class StringColumn extends Column<StringColumn> implements StringExpression {

  protected StringColumn(String columnName, Table table, DataSpec dataSpec, StringExpression generatedFrom,
                         @Nullable StringExpression defaultValue, @Nullable StringExpression onUpdate,
                         BuildingHints hints) {
    super(columnName, table, dataSpec, generatedFrom, defaultValue, onUpdate, hints);
  }

  @Override
  public StringColumn make(String columnName, DataSpec dataSpec, Expression generatedFrom) {
    return new StringColumn(columnName, table, dataSpec, (StringExpression) generatedFrom, (StringExpression) defaultValue,
        (StringExpression) onUpdate, hints);
  }

  @Override
  public StringColumn indexed() {
    return new StringColumn(columnName, table, dataSpec, (StringExpression) generatedFrom, (StringExpression) defaultValue,
        (StringExpression) onUpdate, hints.index());
  }

  @Override
  public StringColumn primary() {
    return null;
  }

  @Override
  public StringColumn unique() {
    return new StringColumn(columnName, table, dataSpec, (StringExpression) generatedFrom, (StringExpression) defaultValue,
        (StringExpression) onUpdate, hints.unique());
  }

  @Override
  public StringColumn invisible() {
    return null;
  }

  @Override
  public StringColumn allowNull() {
    return new StringColumn(columnName, table, dataSpec, (StringExpression) generatedFrom, (StringExpression) defaultValue,
        (StringExpression) onUpdate, hints.allowNull());
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

  @Override
  public StringColumn onUpdate(Enum<?> value) {
    return null;
  }

  public static StringColumn make(String columnName, Table table, int size, StringExpression generatedFrom,
                                  StringExpression defaultValue, StringExpression onUpdate, BuildingHints hints) {
    return new StringColumn(columnName, table, DataSpec.make(DataType.varchar, size), generatedFrom, defaultValue,
        onUpdate, hints);
  }

  public static StringColumn make(String columnName, Table table, DataSpec dataSpec, StringExpression generatedFrom,
                                  StringExpression defaultValue, StringExpression onUpdate, BuildingHints hints) {
    return new StringColumn(columnName, table, dataSpec, generatedFrom, defaultValue,
        onUpdate, hints);
  }

  public static StringColumn make(String columnName, Table table, int size, BuildingHints hints) {
    return new StringColumn(columnName, table, DataSpec.make(DataType.varchar, size), null, null,
        null, BuildingHints.make());
  }

  public static StringColumn make(String columnName, Table table, int size) {
    return new StringColumn(columnName, table, DataSpec.make(DataType.varchar, size), null, null,
        null, BuildingHints.make());
  }

  public static StringColumn make(String columnName, Table table, DataSpec dataSpec) {
    return new StringColumn(columnName, table, dataSpec, null, null, null, BuildingHints.make());
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
    return new StringColumn(columnName, table, dataSpec, (StringExpression) generatedFrom, str, (StringExpression) onUpdate, hints);
  }

  public StringColumn fullText() {
    return new StringColumn(columnName, table, dataSpec, (StringExpression) generatedFrom, (StringExpression) defaultValue,
        (StringExpression) onUpdate, hints.fulltext());
  }
}
