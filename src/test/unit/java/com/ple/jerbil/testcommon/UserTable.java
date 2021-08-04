package com.ple.jerbil.testcommon;

import com.ple.jerbil.sql.expression.Column;
import com.ple.jerbil.sql.fromExpression.Table;

public class UserTable extends Table {

  public final Column userId = Column.make("userId", Column.name, Column.table, Column.dataSpec, Column.indexed, Column.primary).primary();
  public final Column name = Column.make("name", Column.name, Column.table, Column.dataSpec, Column.indexed, Column.primary).varchar(20).indexed();

  public UserTable() {
    super("user");
    userId.table = this;
  }

}
