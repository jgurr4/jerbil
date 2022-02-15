package com.ple.jerbil.testcommon;

import com.ple.jerbil.data.DatabaseContainer;
import com.ple.jerbil.data.query.Table;

public class TestDbTables {

  public final Table user;
  public final Table player;
  public final Table item;
  public final Table inventory;
  public final DatabaseContainer dbContainer;

  public TestDbTables(Table user, Table player, Table item, Table inventory, DatabaseContainer dbContainer) {
    this.user = user;
    this.player = player;
    this.item = item;
    this.inventory = inventory;
    this.dbContainer = dbContainer;
  }

}
