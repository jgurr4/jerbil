package com.ple.jerbil.testcommon;

import com.ple.jerbil.data.Database;
import com.ple.jerbil.data.Immutable;
import com.ple.jerbil.data.StorageEngine;
import com.ple.jerbil.data.query.Table;

@Immutable
public class PlayerTable extends Table {

  public PlayerTable(Database db) {
    super("player", db, StorageEngine.transactional);
  }

}
