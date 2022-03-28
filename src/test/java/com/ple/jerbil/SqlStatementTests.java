package com.ple.jerbil;

import com.ple.jerbil.data.DataGlobal;
import com.ple.jerbil.data.builder.DatabaseBuilderOld;
import com.ple.jerbil.data.builder.DatabaseBuilder;
import com.ple.jerbil.data.bridge.MariadbR2dbcBridge;
import com.ple.jerbil.data.query.CompleteQuery;
import com.ple.jerbil.data.query.PartialInsertQuery;
import com.ple.jerbil.testcommon.*;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Properties;

import static com.ple.jerbil.data.selectExpression.Literal.make;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SqlStatementTests {

  final TestDatabaseContainer testDb = DatabaseBuilderOld.generate(TestDatabaseContainer.class, "test");
  final UserTableContainer user = testDb.user;
  final ItemTableContainer item = testDb.item;
  final PlayerTableContainer player = testDb.player;
  final InventoryTableContainer inventory = testDb.inventory;
  final OrderTableContainer order = testDb.order;

  public SqlStatementTests() {
    final Properties props = ConfigProps.getProperties();
    DataGlobal.bridge = MariadbR2dbcBridge.make(props.getProperty("host"), Integer.parseInt(props.getProperty("port")),
        props.getProperty("user"), props.getProperty("password"));
  }

  @Test
  void testInsertSingle() {
    final PartialInsertQuery base = item.insert();
    final CompleteQuery q = base.set(item.name, make("sword of spirit")).set(
        item.type, make(ItemType.weapon));
    assertEquals("""
        insert into item
        (name, type) values
        ('sword of spirit', 'weapon')
        """, q.toSql());
  }

  //FIXME: Figure out how to get .set() to work without specifying any columns, just values.
  @Test
  void testInsertMulti() {
    final CompleteQuery q1 = item.insert().set(
        List.of(item.name, item.type),
        List.of(
            List.of("sword of spirit", ItemType.weapon.toString()),
            List.of("shield of faith", ItemType.shield.toString()),
            List.of("breastplate of righteousness", ItemType.armor.toString())
        )
    );
//    final CompleteQuery q2 = item.insert().set(
//        List.of(
//            List.of(make(0), make("sword of spirit"), make(ItemType.weapon), make(200)),
//            List.of(make(0), make("serpent barding"), make(ItemType.armor), make(450))
//        )
//    );
    assertEquals("""
        insert into item
        (name, type) values
        ('sword of spirit', 'weapon'),
        ('shield of faith', 'shield'),
        ('breastplate of righteousness', 'armor')
        """, q1.toSql());
//    assertEquals("""
//        insert into item
//        values
//        (0, 'sword of spirit', 'weapon', 200),
//        (0, 'serpent barding', 'armor', 450)
//        """, q2.toSql());
  }

  @Test
  void testMultiTableInsert() {
  }

  @Test
  void testInsertIgnore() {
  }

  @Test
  void testInsertOnDuplicateUpdate() {
  }

  @Test
  void testDelete() {
    final CompleteQuery q = order.delete().where(order.finalized.eq(make(false)));
    assertEquals("""
        delete from `order`
        where finalized = false
        """, q.toSql());
  }

  @Test
  void testMultiTableDelete() {
  }

  @Test
  void testUpdate() {
    final CompleteQuery q = order.update().set(order.finalized, make(true)).set(order.phrase, make("Something here"))
        .where(order.finalized.eq(make(false)));
    assertEquals("""
        update `order`
        set finalized = true,
        phrase = 'Something here'
        where finalized = false
        """, q.toSql());
  }

  @Test
  void testUpdateMulti() {
  }

  @Test
  void testMultiTableUpdate() {
  }

  @Test
  void testReplaceSingle() {
    final CompleteQuery q = player.replace().set(player.name, make("bob"));
    assertEquals("""
        replace into player
        (name) values
        ('bob')
        """, q.toSql());
  }

  @Test
  void testReplaceMulti() {
  }

  @Test
  void testMultiTableReplace() {
  }
}
