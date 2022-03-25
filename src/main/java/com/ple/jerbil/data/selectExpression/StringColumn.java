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

  protected StringColumn(String columnName, Table table, DataSpec dataSpec, @Nullable StringExpression defaultValue,
                         BuildingHints hints) {
    super(columnName, table, dataSpec, defaultValue, hints);
  }

  @Override
  public StringColumn make(String columnName, DataSpec dataSpec) {
    return new StringColumn(columnName, table, dataSpec, (StringExpression) defaultValue, hints);
  }

  public static StringColumn make(String columnName, Table table, int size, StringExpression defaultValue,
                                  BuildingHints hints) {
    return new StringColumn(columnName, table, DataSpec.make(DataType.varchar, size), defaultValue, hints);
  }

  public static StringColumn make(String columnName, Table table, DataSpec dataSpec, StringExpression defaultValue,
                                  BuildingHints hints) {
    return new StringColumn(columnName, table, dataSpec, defaultValue, hints);
  }

  public static StringColumn make(String columnName, Table table, int size, BuildingHints hints) {
    return new StringColumn(columnName, table, DataSpec.make(DataType.varchar, size), null, hints);
  }

  public static StringColumn make(String columnName, Table table, int size) {
    return new StringColumn(columnName, table, DataSpec.make(DataType.varchar, size), null, BuildingHints.make());
  }

  public static StringColumn make(String columnName, Table table, DataSpec dataSpec) {
    return new StringColumn(columnName, table, dataSpec, null, BuildingHints.make());
  }

  @Override
  public StringColumn indexed() {
    return new StringColumn(columnName, table, dataSpec, (StringExpression) defaultValue, hints.index());
  }

  @Override
  public StringColumn primary() {
    return null;
  }

  @Override
  public StringColumn unique() {
    return new StringColumn(columnName, table, dataSpec, null, hints.unique());
  }

  @Override
  public StringColumn invisible() {
    return null;
  }

  @Override
  public StringColumn allowNull() {
    Expression newDefault = defaultValue;
    if (defaultValue == null) {
      newDefault = LiteralNull.instance;
    }
    return new StringColumn(columnName, table, dataSpec, (StringExpression) newDefault, hints.allowNull());
  }

  @Override
  public StringColumn defaultValue(Expression e) {
    return null;
  }

  @Override
  public StringColumn defaultValue(Enum<?> value) {
    return null;
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
    BuildingHints newHints = hints;
    if (str instanceof LiteralNull) {
      newHints = hints.allowNull();
    }
    return new StringColumn(columnName, table, dataSpec, str, newHints);
  }

  public StringColumn fullText() {
    return new StringColumn(columnName, table, dataSpec, (StringExpression) defaultValue, hints.fulltext());
  }

  @Override
  public String toString() {
    return "StringColumn{" +
        "columnName='" + columnName + '\'' +
        ", dataSpec=" + dataSpec +
        ", defaultValue=" + defaultValue +
        ", hints=" + hints +
        ", table=" + table +
        '}';
  }
}
