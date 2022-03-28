package com.ple.jerbil.data.builder;

import com.ple.jerbil.data.BuildingHints;
import com.ple.jerbil.data.query.Table;
import com.ple.jerbil.data.selectExpression.*;

public class StringColumnBuilder extends ColumnBuilder{
  private StringColumn column;

  protected StringColumnBuilder(DatabaseBuilder dbBuild, String columnName, Table table, StringColumn column) {
    super(dbBuild, tblBuild, columnName, table);
    this.column = column;
  }

  public StringColumnBuilder defaultValue(LiteralString barter) {
    return null;
  }

  public StringColumnBuilder unique() {
    return null;
  }

  public StringColumnBuilder fullText() {
    return null;
  }

  public StringColumnBuilder allowNull() {
    return null;
  }

  public StringColumnBuilder indexed() {
    return null;
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
  public StringColumn defaultValue(Enum<?> value) {
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


}
