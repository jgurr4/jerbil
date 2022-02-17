package com.ple.jerbil.testcommon;

import com.ple.jerbil.data.Database;
import com.ple.jerbil.data.DatabaseContainer;
import com.ple.jerbil.data.Immutable;
import com.ple.jerbil.data.query.TableContainer;
import com.ple.util.IMap;

@Immutable
public class TestDatabaseContainer extends DatabaseContainer {

  public final UserTableContainer user;
  public final PlayerTableContainer player;
  public final ItemTableContainer item;
  public final InventoryTableContainer inventory;
  public final IMap<String, TableContainer> tables;

  public TestDatabaseContainer(Database database, UserTableContainer user, PlayerTableContainer player, ItemTableContainer item, InventoryTableContainer inventory,
                               IMap<String, TableContainer> tables) {
    //Optionally add charset value to end of this.
    super(database, tables, null);
    this.user = user;
    this.player = player;
    this.item = item;
    this.inventory = inventory;
    this.tables = tables;
  }

  public static TestDatabaseContainer make()

}