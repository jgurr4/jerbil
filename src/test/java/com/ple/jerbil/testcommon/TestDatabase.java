package com.ple.jerbil.testcommon;

import com.ple.jerbil.data.Database;
import com.ple.jerbil.data.DatabaseContainer;
import com.ple.jerbil.data.Immutable;
import com.ple.jerbil.data.query.TableContainer;
import com.ple.util.IMap;

@Immutable
public class TestDatabase extends DatabaseContainer {

  public final UserTable user;
  public final PlayerTable player;
  public final ItemTable item;
  public final InventoryTable inventory;
  public final IMap<String, TableContainer> tables;

  public TestDatabase(Database database, UserTable user, PlayerTable player, ItemTable item, InventoryTable inventory,
                      IMap<String, TableContainer> tables) {
    //Optionally add charset value to end of this.
    super(database, tables, null);
    this.user = user;
    this.player = player;
    this.item = item;
    this.inventory = inventory;
    this.tables = tables;
  }
}