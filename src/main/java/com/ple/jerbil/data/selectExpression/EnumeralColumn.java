package com.ple.jerbil.data.selectExpression;

import com.ple.jerbil.data.BuildingHints;
import com.ple.jerbil.data.DataSpec;
import com.ple.jerbil.data.query.SelectQuery;
import com.ple.jerbil.data.query.Table;
import org.jetbrains.annotations.Nullable;

public class EnumeralColumn extends Column<EnumeralColumn> implements StringExpression {

  protected EnumeralColumn(String columnName, Table table, DataSpec dataSpec, @Nullable StringExpression defaultValue,
                           BuildingHints hints) {
    super(columnName, table, dataSpec, defaultValue, hints);
  }

  public static EnumeralColumn make(String columnName, Table table, DataSpec dataSpec) {
    return new EnumeralColumn(columnName, table, dataSpec, null, BuildingHints.make());
  }

  @Override
  public EnumeralColumn make(String columnName, DataSpec dataSpec) {
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
    return new EnumeralColumn(columnName, table, dataSpec, (StringExpression) e, hints);
  }

  @Override
  public EnumeralColumn defaultValue(Enum<?> value) {
    return new EnumeralColumn(columnName, table, dataSpec, Literal.make(value.name()), hints);
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
