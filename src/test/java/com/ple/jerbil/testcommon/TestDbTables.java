package com.ple.jerbil.testcommon;

public class TestDbTables {

  public final UserTable user;
  public final PlayerTable player;
  public final ItemTable item;
  public final InventoryTable inventory;

  public TestDbTables(UserTable user, PlayerTable player, ItemTable item, InventoryTable inventory) {
    this.user = user;
    this.player = player;
    this.item = item;
    this.inventory = inventory;
  }

}
