package com.ple.jerbil.testcommon;

import com.ple.jerbil.data.Database;
import com.ple.jerbil.data.DatabaseContainer;
import com.ple.jerbil.data.Immutable;
import com.ple.jerbil.data.query.TableContainer;
import com.ple.util.IArrayList;

@Immutable
public class TestDatabase extends DatabaseContainer {

  public final UserTable user;
  public final PlayerTable player;
  public final ItemTable item;
  public final InventoryTable inventory;
  public final TestDbTables tables;

  public TestDatabase(Database database, UserTable user, PlayerTable player, ItemTable item, InventoryTable inventory,
                      TestDbTables tables) {
    super(database,
          IArrayList.make(TableContainer.make(user, IArrayList.make(user.userId, user.name, user.age)),
                          TableContainer.make(player, IArrayList.make(player.playerId, player.userId, player.name)),
                          TableContainer.make(item, IArrayList.make(item.itemId, item.name, item.type, item.price)),
                          TableContainer.make(inventory, IArrayList.make(
                              inventory.itemId, inventory.playerId)))); //Optionally add charset value to end of this.
    this.user = user;
    this.player = player;
    this.item = item;
    this.inventory = inventory;
    this.tables = tables;
  }

}
