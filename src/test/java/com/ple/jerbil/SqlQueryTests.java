package com.ple.jerbil;

import com.ple.jerbil.data.DataGlobal;
import com.ple.jerbil.data.Database;
import com.ple.jerbil.data.DatabaseContainer;
import com.ple.jerbil.data.bridge.MariadbR2dbcBridge;
import com.ple.jerbil.data.query.CompleteQuery;
import com.ple.jerbil.data.query.Table;
import com.ple.jerbil.data.query.TableContainer;
import com.ple.jerbil.data.selectExpression.Agg;
import com.ple.jerbil.data.selectExpression.Column;
import com.ple.jerbil.testcommon.*;
import com.ple.util.IArrayList;
import com.ple.util.IList;
import org.junit.jupiter.api.Test;

import java.util.Properties;

import static com.ple.jerbil.data.selectExpression.Literal.make;
import static com.ple.jerbil.data.selectExpression.booleanExpression.And.and;
import static com.ple.jerbil.data.selectExpression.booleanExpression.Or.or;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SqlQueryTests {

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

  // To make everything above simpler and auto-generated for the user, I will use one of the methods below, either
  // builder pattern, code generation or reflection.
//  final DatabaseContainer dbContainer = DatabaseContainer.make(testDb, IArrayList.make(user, player, item, inventory));
//  final Database testDb = Database.make("test");
//  final Table player = Table.make("player", testDb);
//  final PlayerColumns playerColumns = new PlayerTableColumns(player);
//  final DatabaseContainer = DatabaseContainer.generate(testDb, IArrayList.make(TableContainer.make(player, playerColumns), TableContainer.make(user, userColumns)));
  // This is builder pattern it is not code generation. It just makes objects. This is hardest for the user, but it is
  // Code generation creates java code before compile time. Code Generation can be 1/5th the code. Code generation is compile time checked and users have to do 1/5th of the code. It can be messier and verbose compared to normal code writing, and it also makes things slightly harder to debug.
  // Reflection generation creates java code after compile time. Reflection users can be 1/3rd the code.

  public SqlQueryTests() {
    final Properties props = ConfigProps.getProperties();
    DataGlobal.bridge = MariadbR2dbcBridge.make(
      props.getProperty("driver"), props.getProperty("host"), Integer.parseInt(props.getProperty("port")),
      props.getProperty("user"), props.getProperty("password")
    );
  }

  @Test
  void testSelect() {

    final CompleteQuery q = user.where(userColumns.name.eq(make("john"))).select(userColumns.userId);
    assertEquals("""
      select userId
      from user
      where name = 'john'
      """, q.toSql());
  }

  @Test
  void multipleWhereConditions() {
    final CompleteQuery q = user.where(
      and(
        userColumns.name.eq(make("john")),
        or(
          userColumns.userId.isGreaterThan(make(4)),
          userColumns.name.eq(make("bob"))
        ),
        userColumns.age.eq(make(30))
      )
    ).select(userColumns.userId);
    assertEquals("""
      select userId
      from user
      where name = 'john'
      and (userId > 4 or name = 'bob')
      and age = 30
      """, q.toSql());
  }

  @Test
  void testReusableQueryBase() {
    final CompleteQuery base = user.select(userColumns.userId);
    final CompleteQuery q1 = base.where(userColumns.name.eq(make("john")));
    final CompleteQuery q2 = base.where(userColumns.name.eq(make("james")));
    assertEquals("""
      select userId
      from user
      where name = 'john'
      """, q1.toSql());

    assertEquals("""
      select userId
      from user
      where name = 'james'
      """, q2.toSql());
  }

  @Test
  void testSelectEnum() {
    final CompleteQuery q = item.where(itemColumns.type.eq(ItemType.weapon.toString())).selectAll();
    assertEquals("""
      select *
      from item
      where type = 'weapon'
      """, q.toSql());

  }

  //FIXME: If you switch player and inventory position, the results won't be what you expect.
  @Test
  void testSelectJoins() {
    final CompleteQuery q = player.join(inventory, item).where(
      and(
        itemColumns.name.eq("bob"),
        playerColumns.name.eq("sword")
      )
    ).select();
    assertEquals("""
      select *
      from player
      inner join inventory using (playerId)
      inner join item using (itemId)
      where item.name = 'bob'
      and player.name = 'sword'
      """, q.toSql());
  }

  @Test
  void testAggregation() {
    final CompleteQuery q = item.select(Agg.count);
    assertEquals("""
      select count(*)
      from item
      """, q.toSql());
  }

  @Test
  void testGroupBy() {
    final CompleteQuery q = item.select(itemColumns.type, Agg.count.as("total")).groupBy(itemColumns.type);
    assertEquals("""
      select type, count(*) as total
      from item
      group by type
      """, q.toSql());
  }

  @Test
  void testComplexExpressions() {
    final CompleteQuery q = item
      .select(itemColumns.price
        .times(make(42))
        .minus(make(1))
        .times(
          make(3)
            .plus(make(1))
        )
        .as("adjustedPrice"))
      .where(itemColumns.price.dividedBy(make(4)).isGreaterThan(make(5)));
    assertEquals("""
      select (price * 42 - 1) * (3 + 1) as adjustedPrice
      from item
      where price / 4 > 5
      """, q.toSql());
  }

  @Test
  void testExpressionWithoutTable() {
    final CompleteQuery q = make(32).minus(make(15)).as("result").select();
    assertEquals("select 32 - 15 as result", q.toSql());
//    Use this to test results from real mysql database, but this code doesn't belong here for unit tests. Only for integration tests.
//    StepVerifier.create(q.execute().flatMap(results -> results.map((row, rowMetadata) -> row.get("result", Integer.class)))
//      .doOnNext(System.out::println))
//      .expectNext(17)
//      .verifyComplete();
  }

  @Test
  void testUnion() {
    final CompleteQuery q = null;
    assertEquals("""
      select userId, name 
      from user 
      union all
      select userId, name 
      from player;
      """, q.toSql());
  }

  @Test
  void testMatchFullText() {
  }

  @Test
  void selectAnalyzeExplain() {
  }

}
