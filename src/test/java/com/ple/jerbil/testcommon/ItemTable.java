package com.ple.jerbil.testcommon;

import com.ple.jerbil.data.Database;
import com.ple.jerbil.data.Immutable;
import com.ple.jerbil.data.query.Table;

@Immutable
public class ItemTable extends Table {

  public ItemTable(Database db) {
    super("item", db);
  }
}
