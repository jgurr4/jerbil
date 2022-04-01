package com.ple.jerbil.data.builder;

import com.ple.jerbil.data.BuildingHints;
import com.ple.jerbil.data.query.Table;
import com.ple.jerbil.data.selectExpression.*;

public class StringColumnBuilder extends ColumnBuilder{
  public StringColumn column;
  public BuildingHints hints;

  protected StringColumnBuilder(DatabaseBuilder dbBuild, TableBuilder tblBuild, String columnName, Table table, StringColumn column, BuildingHints hints) {
    super(dbBuild, tblBuild, columnName, table);
    this.column = column;
    this.hints = hints;
  }

  public static StringColumnBuilder make(DatabaseBuilder dbBuild, TableBuilder tblBuild, StringColumn column) {
    return new StringColumnBuilder(dbBuild, tblBuild, column.getColumnName(), column.table, column, BuildingHints.empty);
  }

  public StringColumn defaultValue(Enum<?> value) {
    return null;
  }

  public StringColumnBuilder defaultValue(StringExpression defaultValue) {
    if (defaultValue instanceof LiteralNull) {
      hints = hints.allowNull();
    }
    column = StringColumn.make(column.columnName, column.table, column.dataSpec, defaultValue, column.props);
    return this;
  }

  public StringColumnBuilder unique() {
    hints = hints.unique();
    return this;
  }

  public StringColumnBuilder fullText() {
    hints = hints.fulltext();
    return this;
  }

  public StringColumnBuilder allowNull() {
    Expression newDefault = column.defaultValue;
    if (newDefault == null) {
      newDefault = LiteralNull.instance;
    }
    column = StringColumn.make(column.columnName, column.table, column.dataSpec, (StringExpression) newDefault, column.props.allowNull());
    hints = hints.allowNull();
    return this;
  }

  public StringColumnBuilder indexed() {
    hints = hints.index();
    return this;
  }

}
