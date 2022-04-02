package com.ple.jerbil.data.builder;

import com.ple.jerbil.data.BuildingHints;
import com.ple.jerbil.data.query.Table;
import com.ple.jerbil.data.selectExpression.*;

import java.time.LocalDateTime;

public class DateColumnBuilder extends ColumnBuilder {
  private DateColumn column;
  private BuildingHints hints;

  protected DateColumnBuilder(DatabaseBuilder dbBuild, TableBuilder tblBuild, String columnName, Table table, DateColumn column, BuildingHints hints) {
    super(dbBuild, tblBuild, columnName, table);
    this.column = column;
    this.hints = hints;
  }

  public static DateColumnBuilder make(DatabaseBuilder dbBuild, TableBuilder tblBuild, DateColumn column) {
    return null;
  }

  public DateColumnBuilder indexed() {
    hints = hints.index();
    return this;
  }

  public DateColumnBuilder primary() {
    hints = hints.primary();
    return this;
  }

  public DateColumnBuilder unique() {
    hints = hints.unique();
    return this;
  }

  public DateColumnBuilder invisible() {
    DateExpression val = (DateExpression) column.defaultValue;
    if (column.defaultValue == null) {
      val = LiteralNull.instance;
    }
    column = DateColumn.make(getColumnName(), getTable(), column.dataSpec, val, column.props.invisible());
    return this;
  }

  public DateColumnBuilder allowNull() {
    column = DateColumn.make(getColumnName(), getTable(), column.dataSpec, (DateExpression) column.defaultValue, column.props.allowNull());
    return this;
  }

  public DateColumnBuilder defaultValue(DateExpression e) {
    column = DateColumn.make(getColumnName(), getTable(), column.dataSpec, e, column.props);
    return this;
  }

  public DateColumnBuilder defaultValue(LocalDateTime e) {
    column = DateColumn.make(getColumnName(), getTable(), column.dataSpec, LiteralDate.make(e), column.props);
    return this;
  }

  public DateColumnBuilder onUpdateCurrentTimeStamp() {
    hints = hints.autoUpdateTime();
    return this;
  }
}
