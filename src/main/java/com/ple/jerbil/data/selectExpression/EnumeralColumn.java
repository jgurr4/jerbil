package com.ple.jerbil.data.selectExpression;

import com.ple.jerbil.data.BuildingHints;
import com.ple.jerbil.data.DataSpec;
import com.ple.jerbil.data.query.SelectQuery;
import com.ple.jerbil.data.query.Table;
import org.jetbrains.annotations.Nullable;

public class EnumeralColumn extends Column<EnumeralColumn> implements StringExpression {

  protected EnumeralColumn(String columnName, Table table, DataSpec dataSpec,
                           @Nullable Expression generatedFrom, @Nullable StringExpression defaultValue,
                           @Nullable StringExpression onUpdate, @Nullable BuildingHints hints) {
    super(columnName, table, dataSpec, generatedFrom, defaultValue, onUpdate, hints);
  }

  public static EnumeralColumn make(String columnName, Table table, DataSpec dataSpec) {
    return new EnumeralColumn(columnName, table, dataSpec, null, null, null, null);
  }

  @Override
  public EnumeralColumn make(String columnName, DataSpec dataSpec, Expression generatedFrom) {
    return null;
  }

  @Override
  public EnumeralColumn indexed() {
    return null;
  }

  @Override
  public EnumeralColumn primary() {
    return null;
  }

  @Override
  public EnumeralColumn unique() {
    return null;
  }

  @Override
  public EnumeralColumn invisible() {
    return null;
  }

  @Override
  public EnumeralColumn allowNull() {
    return null;
  }

  @Override
  public EnumeralColumn defaultValue(Expression e) {
    return new EnumeralColumn(columnName, table, dataSpec, generatedFrom, (StringExpression) e,
        (StringExpression) onUpdate, hints);
  }

  @Override
  public EnumeralColumn defaultValue(Enum<?> value) {
    return new EnumeralColumn(columnName, table, dataSpec, generatedFrom, Literal.make(value.name()),
        (StringExpression) onUpdate, hints);
  }

  @Override
  public EnumeralColumn onUpdate(Expression e) {
    return null;
  }

  @Override
  public EnumeralColumn onUpdate(Enum<?> value) {
    return null;
  }

  @Override
  public AliasedExpression as(String name) {
    return null;
  }

  @Override
  public SelectQuery select() {
    return null;
  }
}
