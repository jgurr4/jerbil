package com.ple.jerbil.data.builder;

import com.ple.jerbil.data.BuildingHints;
import com.ple.jerbil.data.query.Table;
import com.ple.jerbil.data.selectExpression.*;
import com.ple.jerbil.data.selectExpression.booleanExpression.BooleanExpression;

public class BooleanColumnBuilder extends ColumnBuilder {
  private BooleanColumn column;
  private BuildingHints hints;

  protected BooleanColumnBuilder(DatabaseBuilder dbBuild, TableBuilder tblBuild, String columnName, Table table, BooleanColumn column, BuildingHints hints) {
    super(dbBuild, tblBuild, columnName, table);
    this.column = column;
    this.hints = hints;
  }

  public static BooleanColumnBuilder make(DatabaseBuilder dbBuild, TableBuilder tblBuild, BooleanColumn column) {
    return new BooleanColumnBuilder(dbBuild, tblBuild, column.columnName, column.table, column, BuildingHints.empty);
  }

  public BooleanColumnBuilder indexed() {
    hints = hints.index();
    return this;
  }

  public BooleanColumnBuilder primary() {
    hints = hints.primary();
    return this;
  }

  public BooleanColumnBuilder unique() {
    hints = hints.unique();
    return this;
  }

  public BooleanColumnBuilder invisible() {
    BooleanExpression val = (BooleanExpression) column.defaultValue;
    if (column.defaultValue == null) {
      val = LiteralNull.instance;
    }
    column = BooleanColumn.make(getColumnName(), getTable(), column.dataSpec, val, column.props.invisible());
    return this;
  }

  public BooleanColumnBuilder allowNull() {
    column = BooleanColumn.make(getColumnName(), getTable(), column.dataSpec, (BooleanExpression) column.defaultValue, column.props.allowNull());
    return this;
  }

  public BooleanColumnBuilder defaultValue(BooleanExpression e) {
    column = BooleanColumn.make(getColumnName(), getTable(), column.dataSpec, e, column.props);
    return this;
  }

}
