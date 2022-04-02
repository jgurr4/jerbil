package com.ple.jerbil.data.builder;

import com.ple.jerbil.data.BuildingHints;
import com.ple.jerbil.data.query.Table;
import com.ple.jerbil.data.selectExpression.*;

public class EnumeralColumnBuilder extends ColumnBuilder{
  private EnumeralColumn column;
  private BuildingHints hints;

  protected EnumeralColumnBuilder(DatabaseBuilder dbBuild, TableBuilder tblBuild, String columnName, Table table, EnumeralColumn column, BuildingHints hints) {
    super(dbBuild, tblBuild, columnName, table);
    this.column = column;
    this.hints = hints;
  }

  public static EnumeralColumnBuilder make(DatabaseBuilder dbBuild, TableBuilder tblBuild, EnumeralColumn column) {
    return new EnumeralColumnBuilder(dbBuild, tblBuild, column.getColumnName(), column.table, column, BuildingHints.empty);
  }

  public EnumeralColumnBuilder indexed() {
    hints = hints.index();
    return this;
  }

  public EnumeralColumnBuilder primary() {
    hints = hints.primary();
    return this;
  }

  public EnumeralColumnBuilder unique() {
    hints = hints.unique();
    return this;
  }

  public EnumeralColumnBuilder invisible() {
    StringExpression val = (StringExpression) column.defaultValue;
    if (column.defaultValue == null) {
      val = LiteralNull.instance;
    }
    column = EnumeralColumn.make(getColumnName(), getTable(), column.dataSpec, val, column.props.invisible());
    return this;
  }

  public EnumeralColumnBuilder allowNull() {
    column = EnumeralColumn.make(getColumnName(), getTable(), column.dataSpec, (StringExpression) column.defaultValue, column.props.allowNull());
    return this;
  }

  public EnumeralColumnBuilder defaultValue(StringExpression e) {
    column = EnumeralColumn.make(getColumnName(), getTable(), column.dataSpec, e, column.props);
    return this;
  }

  public EnumeralColumnBuilder defaultValue(Enum<?> value) {
    column = EnumeralColumn.make(getColumnName(), getTable(), column.dataSpec, Literal.make(value.name()), column.props);
    return this;
  }

}
