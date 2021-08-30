package com.ple.jerbil;

import com.ple.jerbil.sql.Database;
import com.ple.jerbil.sql.expression.Literal;
import com.ple.jerbil.sql.query.CompleteQuery;
import com.ple.jerbil.testcommon.*;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SqlStatementTests {

  final UserTable user = new UserTable();
  final UserTableColumns userColumns = new UserTableColumns(user);
  final PlayerTable player = new PlayerTable();
  final PlayerTableColumns playerColumns = new PlayerTableColumns(player);
  final ItemTable item = new ItemTable();
  final ItemTableColumns itemColumns = new ItemTableColumns(item);
  final InventoryTable inventory = new InventoryTable();
  final InventoryTableColumns inventoryColumns = new InventoryTableColumns(inventory);

  final Database testDb = Database.make("test").add(user, player, item, inventory);

  @Test
  void testInsertSingle() {

/*
    for (int i = 0; i < records.length; i++) { // This is bad because it makes multiple insert statements. You should be able to do bulk insert statements. Like 'insert into table values (val1, val2),(val1,val2)...'
      item.insert().set(itemColumns.name, Literal.make(someString)).set(itemColumns.type, Literal.make(ItemType.weapon.toString()));
    }
*/
    final CompleteQuery q = item.insert().set(itemColumns.name, Literal.make("sword of spirit")).set(itemColumns.type, Literal.make(ItemType.weapon.toString()));
    assertEquals(q.toString(), """
      insert into item
      set name='sword of spirit',
      type='weapon'
      """);

  }

  @Test
  void testInsertMulti() {

    final CompleteQuery q = item.insert().set(
      List.of(itemColumns.name, itemColumns.type),
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

    final CompleteQuery q = item.create();
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

    final CompleteQuery q = inventory.create();
    assertEquals(q.toString(), """
      create table inventory (
        player long not null,
        itemId long not null,
        primary key (player, itemId)
      ) ENGINE=Aria
      """);

  }

}
