package com.ple.jerbil.testcommon;

import com.ple.jerbil.sql.expression.Column;
import com.ple.jerbil.sql.fromExpression.Table;

public class UserTable extends Table {

  public final Column userId = Column.make("userId", this).primary();
  public final Column name = Column.make("name",this).varchar(20).indexed();

  public UserTable() {
    super("user");
  }

}
