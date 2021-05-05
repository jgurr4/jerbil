package com.ple.jerbil.testcommon;

import com.ple.jerbil.Column;
import com.ple.jerbil.Table;

public class UserTable extends Table {

  final Column userId = Column.make("userId").primary();
  final Column name = Column.make("name").varchar(20).indexed();

  public UserTable() {
    super("user");
  }

}
