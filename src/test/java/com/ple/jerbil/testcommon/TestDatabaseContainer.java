package com.ple.jerbil.testcommon;

import com.ple.jerbil.data.Database;
import com.ple.jerbil.data.DatabaseContainer;
import com.ple.jerbil.data.GenericInterfaces.Immutable;
import com.ple.jerbil.data.query.TableContainer;
import com.ple.util.IArrayMap;
import com.ple.util.IMap;

@Immutable
public class TestDatabaseContainer extends DatabaseContainer {

  public final UserTableContainer user;
  public final PlayerTableContainer player;
  public final ItemTableContainer item;
  public final InventoryTableContainer inventory;
  public final OrderTableContainer order;
  public final IMap<String, TableContainer> tables;

  protected TestDatabaseContainer(Database database, UserTableContainer user, PlayerTableContainer player,
                                  ItemTableContainer item, InventoryTableContainer inventory,
                                  OrderTableContainer order, IMap<String, TableContainer> tables) {
    //Optionally add charset value to end of this.
    super(database, tables, null);
    this.user = user;
    this.player = player;
    this.item = item;
    this.inventory = inventory;
    this.order = order;
    this.tables = tables;
  }

  public static TestDatabaseContainer make(Database database, UserTableContainer user, PlayerTableContainer player,
                                           ItemTableContainer item, InventoryTableContainer inventory,
                                           OrderTableContainer order) {
    final IMap<String, TableContainer> tables = IArrayMap.make(user.tableName, user, player.tableName, player,
        item.tableName, item, inventory.tableName, inventory, order.tableName, order);
    return new TestDatabaseContainer(database, user, player, item, inventory, order, tables);
  }

}