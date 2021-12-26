package com.ple.jerbil;

import com.ple.jerbil.data.DataGlobal;
import com.ple.jerbil.data.Database;
import com.ple.jerbil.data.bridge.MysqlBridge;
import com.ple.jerbil.data.query.CompleteQuery;
import com.ple.jerbil.data.selectExpression.Literal;
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

  public SqlStatementTests() {
    DataGlobal.bridge = MysqlBridge.make();
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

  @Test
  void testTableCreate() {

    final CompleteQuery q = item.create();
    assertEquals("""
      create table item (
        itemId bigint not null primary key,
        name varchar(20) not null,
        type enum('weapon', 'armor', 'shield', 'accessory') not null,
        price int not null
      ) ENGINE=Aria
      """, q.toSql());

  }

  @Test
  void testMultiColumnPrimaryKey() {

    final CompleteQuery q = inventory.create();
    assertEquals("""
      create table inventory (
        playerId bigint not null,
        itemId bigint not null,
        primary key (playerId, itemId)
      ) ENGINE=Aria
      """, q.toSql());

  }

}
