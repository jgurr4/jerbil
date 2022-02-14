package com.ple.jerbil.testcommon;

import com.ple.jerbil.data.Database;
import com.ple.jerbil.data.Immutable;
import com.ple.jerbil.data.query.Table;

@Immutable
public class UserTable extends Table {

  public UserTable(Database db) {
    super("user", db);
  }

}
