package com.ple.jerbil;

import com.ple.jerbil.data.DataGlobal;
import com.ple.jerbil.data.Database;
import com.ple.jerbil.data.DatabaseContainer;
import com.ple.jerbil.data.bridge.MariadbR2dbcBridge;
import com.ple.jerbil.data.query.CompleteQuery;
import com.ple.jerbil.data.query.TableContainer;
import com.ple.jerbil.data.selectExpression.Literal;
import com.ple.jerbil.testcommon.*;
import com.ple.util.IArrayList;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SqlStatementTests {

  final Database testDb = Database.make("test");  //Can add ("test", Charset.utf8) here as well in future.
  final UserTable user = new UserTable(testDb);
  final UserTableColumns userColumns = new UserTableColumns(user);
  final TableContainer userTableContainer = TableContainer.make(user, userColumns.columns);
  final PlayerTable player = new PlayerTable(testDb);
  final PlayerTableColumns playerColumns = new PlayerTableColumns(player);
  final TableContainer playerTableContainer = TableContainer.make(player, playerColumns.columns);
  final ItemTable item = new ItemTable(testDb);
  final ItemTableColumns itemColumns = new ItemTableColumns(item);
  final TableContainer itemTableContainer = TableContainer.make(item, itemColumns.columns);
  final InventoryTable inventory = new InventoryTable(testDb);
  final InventoryTableColumns inventoryColumns = new InventoryTableColumns(inventory);
  final TableContainer inventoryTableContainer = TableContainer.make(inventory, inventoryColumns.columns);
  final DatabaseContainer dbContainer = DatabaseContainer.make(
    testDb, IArrayList.make(userTableContainer, playerTableContainer, itemTableContainer, inventoryTableContainer));

  public SqlStatementTests() {
    final Properties props = ConfigProps.getProperties();
    DataGlobal.bridge = MariadbR2dbcBridge.make(props.getProperty("driver"), props.getProperty("host"), Integer.parseInt(props.getProperty("port")), props.getProperty("user"), props.getProperty("password"));
  }

  @Test
  void testInsertSingle() {
    final CompleteQuery q = item.insert().set(itemColumns.name, Literal.make("sword of spirit")).set(
      itemColumns.type,
      Literal.make(ItemType.weapon.toString())
    );
    assertEquals("""
      insert into item
      (name, type) values
      ('sword of spirit', 'weapon')
      """, q.toSql());
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
    assertEquals("""
      insert into item
      (name, type) values
      ('sword of spirit', 'weapon'),
      ('shield of faith', 'shield'),
      ('breastplate of righteousness', 'armor')
      """, q.toSql());
  }

/*
  @Test
  void testUpdateSingle() {
  }

  @Test
  void testUpdateMulti() {
  }

  @Test
  void testDeleteSingle() {
  }

  @Test
  void testDeleteMulti() {
  }

  @Test
  void testReplaceSingle() {
  }

  @Test
  void testReplaceMulti() {
  }
*/

  @Test
  void testTableCreate() {
    final CompleteQuery q = item.create();
    assertEquals("""
      create table item (
        itemId int auto_increment,
        name varchar(20) not null,
        type enum('weapon','armor','shield','accessory') not null,
        price int not null,
        primary key (itemId),
        key nm_idx (name)
      ) ENGINE=Aria
      """, q.toSql());
  }

  @Test
  void testMultiColumnPrimaryKey() {
    final CompleteQuery q = inventory.create();
    assertEquals("""
      create table inventory (
        playerId int,
        itemId int,
        primary key (playerId,itemId)
      ) ENGINE=Aria
      """, q.toSql());
  }
}
