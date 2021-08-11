package com.ple.jerbil.testcommon;

import com.ple.jerbil.sql.expression.Column;
import com.ple.jerbil.sql.StorageEngine;
import com.ple.jerbil.sql.fromExpression.Table;
import com.ple.util.IHashMap;

public class PlayerTable extends Table {


  public PlayerTable() {
    super(StorageEngine.transactional, "player", IHashMap.empty);
  }

}
