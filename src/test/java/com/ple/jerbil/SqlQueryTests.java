package com.ple.jerbil;

import com.ple.jerbil.data.*;
import com.ple.jerbil.data.bridge.MariadbR2dbcBridge;
import com.ple.jerbil.data.query.CompleteQuery;
import com.ple.jerbil.data.selectExpression.Agg;
import com.ple.jerbil.data.selectExpression.Column;
import com.ple.jerbil.data.selectExpression.NumericExpression.NumericColumn;
import com.ple.jerbil.testcommon.*;
import com.ple.util.IList;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;

import java.util.Properties;

import static com.ple.jerbil.data.selectExpression.Literal.make;
import static com.ple.jerbil.data.selectExpression.booleanExpression.And.and;
import static com.ple.jerbil.data.selectExpression.booleanExpression.Or.or;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SqlQueryTests {

  final TestDatabaseContainer testDb = DatabaseBuilder.generate(TestDatabaseContainer.class, "test");
  final UserTableContainer user = testDb.user;
  final ItemTableContainer item = testDb.item;
  final PlayerTableContainer player = testDb.player;
  final InventoryTableContainer inventory = testDb.inventory;
  final OrderTableContainer order = testDb.order;

  public SqlQueryTests() {
    final Properties props = ConfigProps.getProperties();
    DataGlobal.bridge = MariadbR2dbcBridge.make(
        props.getProperty("driver"), props.getProperty("host"), Integer.parseInt(props.getProperty("port")),
        props.getProperty("user"), props.getProperty("password")
    );
  }

/*
  @Test
  void temporaryTest() {
    //This more verbose method makes it so that column names will never conflict with Database or Tables properties/fields.
    Column itemIdVerbose = testDb.tables.get("inventory").columns.get("itemId");
    NumericColumn itemId = testDb.inventory.itemId;
    CharSet charSet = testDb.charSet;
    NumericColumn playerId = testDb.inventory.playerId;
    StorageEngine storageEngine = testDb.item.storageEngine;
    DataSpec dataSpec = testDb.item.itemId.dataSpec;
    final IList<Index> indexes = testDb.order.indexes;
    testDb.sync();
    testDb.item.sync();
    testDb.item.itemId.sync();
    InventoryTableContainer inventory = testDb.inventory;
    ItemTableContainer item = testDb.item;
    CompleteQuery q = testDb.inventory.select().where(inventory.itemId.eq(make(3)));
    String name = q.execute().unwrapFlux().flatMap(result -> result.map((row, rowMetadata) -> (String) row.get("name")))
        .blockFirst();
    Mono<String> rName = q.execute().unwrapFlux()
        .flatMap(result -> result.map((row, rowMetadata) -> (String) row.get("name"))).next();
  }
*/

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

  //FIXME
  @Test
  void testSelectEnum() {
    final CompleteQuery q = item.where(item.type.eq(ItemType.weapon)).selectAll();
    assertEquals("""
        select *
        from item
        where type = 'weapon'
        """, q.toSql());
  }

  //FIXME
  @Test
  void testLimitOffset() {
    final CompleteQuery q = player.select().limit(5, 10);
    assertEquals("""
        select *
        from player
        limit 10, 10
        """, q.toSql());
  }

  //FIXME: test switching player and inventory position, see if results are what you expect.
  //TODO: Make alternative method: player.select().join().where();
  //TODO: Make leftJoin, rightJoin, and fullJoin.
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

  //FIXME
  @Test
  void testGroupBy() {
    final CompleteQuery q = item.select(item.type, Agg.count.as("total")).groupBy(item.type);
    assertEquals("""
        select type, count(*) as total
        from item
        group by type
        """, q.toSql());
  }

  //FIXME
  @Test
  void testHaving() {
    final CompleteQuery q = item.select(item.name, Agg.sum(item.price)).groupBy(item.name)
        .having(item.price.gt(make(100.00)));
    assertEquals("""
        select name, sum(price)
        from item
        group by name
        having price > 100.00
        """, q.toSql());
  }

  @Test
  void testRollup() {
    //TODO: Implement this.
  }

  //FIXME
  @Test
  void testOrderBy() {
    final CompleteQuery q = item.select(item.name, item.price).where(item.price.ge(make(2.32)))
        .orderBy(item.price, Order.descending);
    assertEquals("""
        select name, price
        from item
        where price >= 2.32
        order by price desc
        """, q.toSql());
  }

  //FIXME
  @Test
  void testSelectDistinct() {
    final CompleteQuery q = order.selectDistinct(order.total).where(order.finalized);
    assertEquals("""
        select distinct total
        from `order`
        where finalized = true
        """, q.toSql());
  }

  //FIXME
  @Test
  void testSelectNull() {
    final CompleteQuery q = item.select(item.itemId).where(item.type.isNull())
        .union(item.select(item.itemId).where(item.type.isNotNull()));
    assertEquals("""
        select itemId
        from item
        where type is null
        union
        select itemId
        from item
        where type is not null
        """, q.toSql());
  }

  //FIXME
  @Test
  void testSelectRegexp() {
    final CompleteQuery q = item.select(item.name).where(item.name.isRegexp(make(".*ohn.*")))
        .union(item.select(item.name).where(item.name.isNotRegexp(make(".*ohn.*"))));
    assertEquals("""
        select name
        from item
        where name regexp '.*ohn.*'
        union
        select name
        from item
        where name not regexp '.*ohn.*'
        """, q.toSql());
  }

  //FIXME
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

  //FIXME
  @Test
  void testExpressionWithoutTable() {
    final CompleteQuery q = make(32).minus(make(15)).as("result").select();
    assertEquals("select 32 - 15 as result", q.toSql());
  }

  //FIXME
  @Test
  void testUnionAndBetween() {
    final CompleteQuery q = user.select(user.userId, user.name).where(user.userId.isBetween(make(4), make(10)))
        .union(user.select(user.userId, user.name).where(user.userId.isNotBetween(make(4), make(10))));
    assertEquals("""
        select userId, name
        from user
        where userId between 4 and 10
        union
        select userId, name
        from player
        where userId not between 4 and 10
        """, q.toSql());
  }

  //FIXME
  @Test
  void testUnionAllAndLike() {
    final CompleteQuery q = user.select(user.userId, user.name).where(user.name.isLike(make("%oh%")))
        .unionAll(user.select(user.userId, user.name).where(user.name.isNotLike(make("%oh%"))));
    assertEquals("""
        select userId, name
        from user
        where name like '%oh%'
        union all
        select userId, name
        from player
        where name not like '%oh%'
        """, q.toSql());
  }

  //FIXME
  @Test
  void testMatchFullText() {
    final CompleteQuery q = order.select(order.phrase).whereMatch(order.phrase, make("Hello there"));
    assertEquals("""
        select phrase from order
        where match (phrase)
        against ('hello there')
        """, q.toSql());
  }

  //FIXME
  @Test
  void testExplain() {
    final CompleteQuery q1 = order.select().explain();
    final CompleteQuery q2 = order.explain().select();
    assertEquals("""
        explain select *
        from order
        """, q1.toSql());
    assertEquals("""
        explain select *
        from order
        """, q2.toSql());
  }

  //FIXME
  @Test
  void testAnalyze() { //For mysqlbridge it would have to do explain analyze select, but for mariadbbridge it would just do analyze select.
    final CompleteQuery q1 = order.select().analyze();
    final CompleteQuery q2 = order.analyze().select();
    assertEquals("""
        analyze select *
        from order
        """, q1.toSql());
    assertEquals("""
        analyze select *
        from order
        """, q2.toSql());
  }

}
