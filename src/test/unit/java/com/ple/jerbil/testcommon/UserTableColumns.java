package com.ple.jerbil.testcommon;

import com.ple.jerbil.sql.Immutable;
import com.ple.jerbil.sql.expression.Column;
import com.ple.jerbil.sql.fromExpression.Table;

@Immutable
public class UserTableColumns {

  public final Column userId;
  public final Column name;

  public UserTableColumns(Table table) {
    userId = Column.make("userId", table).primary();
    name = Column.make("name",table).varchar(20).indexed();
  }
}
