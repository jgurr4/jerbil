package com.ple.jerbil.testcommon;

import com.ple.jerbil.data.StorageEngine;
import com.ple.jerbil.data.query.Table;
import com.ple.util.IArrayList;
import com.ple.util.IHashMap;

public class PlayerTable extends Table {


  public PlayerTable() {
    super(StorageEngine.transactional, "player", IArrayList.make());
  }

}
