package com.ple.jerbil;

import com.ple.jerbil.testcommon.*;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SqlStatementTests {

  final UserTable user = new UserTable();
  final PlayerTable player = new PlayerTable();
  final ItemTable item = new ItemTable();
  final InventoryTable inventory = new InventoryTable();

  final Database testDb = Database.from("test").add(user, player, item, inventory);

  @Test
  void testInsertSingle() {

    final Query q = item.insert().set(item.name, Literal.from("sword of spirit")).set(item.type, Literal.from(ItemType.weapon.toString()));
    assertEquals(q.toString(), """
      insert into item
      set name='sword of spirit',
      type='weapon'
      """);

  }

  @Test
  void testInsertMulti() {

    final Query q = item.insert().set(
      List.of(item.name, item.type),
      List.of(
        List.of("sword of spirit", ItemType.weapon.toString()),
        List.of("shield of faith", ItemType.shield.toString()),
        List.of("breastplate of righteousness", ItemType.armor.toString())
      )
    );
    assertEquals(q.toString(), """
      insert into item
      (name, type) values 
      ('sword of spirit', 'weapon'),
      ('shield of faith', 'shield'),
      ('breastplate of righteousness', 'armor')
      """);

  }

  @Test
  void testTableCreate() {

    final Query q = item.create();
    assertEquals(q.toString(), """
      create table item (
        itemId long not null primary key,
        name varchar(255) not null,
        type enum('weapon', 'armor', 'shield',  'accessory') not null
      ) ENGINE=Aria
      """);

  }

  @Test
  void testMultiColumnPrimaryKey() {

    final Query q = inventory.create();
    assertEquals(q.toString(), """
      create table inventory (
        player long not null,
        itemId long not null,
        primary key (player, itemId)
      ) ENGINE=Aria
      """);

  }

}
