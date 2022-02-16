package com.ple.jerbil;

import com.ple.jerbil.data.*;
import com.ple.jerbil.data.bridge.MariadbR2dbcBridge;
import com.ple.jerbil.data.query.CompleteQuery;
import com.ple.jerbil.data.selectExpression.Agg;
import com.ple.jerbil.data.selectExpression.NumericExpression.NumericColumn;
import com.ple.jerbil.testcommon.*;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;

import java.util.Properties;

import static com.ple.jerbil.data.selectExpression.Literal.make;
import static com.ple.jerbil.data.selectExpression.booleanExpression.And.and;
import static com.ple.jerbil.data.selectExpression.booleanExpression.Or.or;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SqlQueryTests {

  final TestDatabase testDb = DatabaseBuilder.generate(TestDatabase.class);
  // With this method, all the code boilerplate code is no longer needed. Users can still use the manual method.
  // But using the DatabaseBuilder instead will make things much simpler and reduce all the boilerplate code.
  UserTable user = testDb.user;
  ItemTable item = testDb.item;
  PlayerTable player = testDb.player;
  InventoryTable inventory = testDb.inventory;

  public SqlQueryTests() {
    final Properties props = ConfigProps.getProperties();
    DataGlobal.bridge = MariadbR2dbcBridge.make(
      props.getProperty("driver"), props.getProperty("host"), Integer.parseInt(props.getProperty("port")),
      props.getProperty("user"), props.getProperty("password")
    );
  }

  @Test
  void temporaryTest() {
// Deciding between less verbose when reading or more clear when writing.
    //This more verbose method makes it so that column names will never conflict with Database/Table properties/fields.
    NumericColumn itemId = testDb.tables.inventory.columns.itemId;
    //Less verbose for readers.
//    CharSet charSet = testDb.charset;   //This would require the user to do more work to add Charset as field of TestDatabase class.
    CharSet charSet = testDb.database.charSet;
    NumericColumn playerId = testDb.inventory.playerId;
    StorageEngine storageEngine = testDb.inventory.storageEngine;
    DataSpec dataSpec = testDb.inventory.itemId.dataSpec;
    testDb.sync();
    testDb.inventory.sync();
    testDb.inventory.itemId.sync();
    InventoryTable inventory = testDb.inventory;
    CompleteQuery q = testDb.inventory.select().where(inventory.itemId.eq(make(3)));
    String name = q.execute().unwrapFlux().flatMap(result -> result.map((row, rowMetadata) -> (String) row.get("name"))).blockFirst();
    Mono<String> rName = q.execute().unwrapFlux().flatMap(result -> result.map((row, rowMetadata) -> (String) row.get("name"))).next();

    //We are between deciding whether to separate the concept of TestDatabase and the list of tables/columns.

  }

  @Test
  void testSelect() {
    final CompleteQuery q = user.where(user.name.eq(make("john"))).select(user.userId);
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
        user.name.eq(make("john")),
        or(
          user.userId.isGreaterThan(make(4)),
          user.name.eq(make("bob"))
        ),
        user.age.eq(make(30))
      )
    ).select(user.userId);
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
    final CompleteQuery base = user.select(user.userId);
    final CompleteQuery q1 = base.where(user.name.eq(make("john")));
    final CompleteQuery q2 = base.where(user.name.eq(make("james")));
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
    final CompleteQuery q = item.where(item.type.eq(ItemType.weapon.toString())).selectAll();
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
        item.name.eq("bob"),
        player.name.eq("sword")
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
    final CompleteQuery q = item.select(item.type, Agg.count.as("total")).groupBy(item.type);
    assertEquals("""
      select type, count(*) as total
      from item
      group by type
      """, q.toSql());
  }

  @Test
  void testComplexExpressions() {
    final CompleteQuery q = item
      .select(item.price
        .times(make(42))
        .minus(make(1))
        .times(
          make(3)
            .plus(make(1))
        )
        .as("adjustedPrice"))
      .where(item.price.dividedBy(make(4)).isGreaterThan(make(5)));
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
