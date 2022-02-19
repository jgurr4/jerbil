package com.ple.jerbil;

import com.ple.jerbil.data.DataGlobal;
import com.ple.jerbil.data.DatabaseBuilder;
import com.ple.jerbil.data.bridge.MariadbR2dbcBridge;
import com.ple.jerbil.data.query.CompleteQuery;
import com.ple.jerbil.data.selectExpression.Literal;
import com.ple.jerbil.testcommon.*;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Properties;

import static com.ple.jerbil.data.selectExpression.Literal.make;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SqlStatementTests {

  final TestDatabaseContainer testDb = DatabaseBuilder.generate(TestDatabaseContainer.class, "test");
  final UserTableContainer user = testDb.user;
  final ItemTableContainer item = testDb.item;
  final PlayerTableContainer player = testDb.player;
  final InventoryTableContainer inventory = testDb.inventory;
  final OrderTableContainer order = testDb.order;

  public SqlStatementTests() {
    final Properties props = ConfigProps.getProperties();
    DataGlobal.bridge = MariadbR2dbcBridge.make(props.getProperty("driver"), props.getProperty("host"), Integer.parseInt(props.getProperty("port")), props.getProperty("user"), props.getProperty("password"));
  }

  @Test
  void testInsertSingle() {
    final CompleteQuery q = item.insert().set(item.name, Literal.make("sword of spirit")).set(
      item.type,
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
      List.of(item.name, item.type),
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
  void testDelete() {
    final CompleteQuery q = order.delete().where(order.finalized.eq(make(false)));
    assertEquals("""
        delete from order
        where finalized = false
        """, q.toSql());
  }

  @Test
  void testUpdate() {
    final CompleteQuery q = order.update().set(order.finalized, make(true)).set(order.phrase, make("Something here"))
        .where(order.finalized.eq(make(false)));
    assertEquals("""
        update order
        set finalized = true,
        phrase = 'Something here'
        where finalized = false""", q.toSql());
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
