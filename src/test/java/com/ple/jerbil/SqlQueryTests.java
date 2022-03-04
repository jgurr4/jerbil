package com.ple.jerbil;

import com.ple.jerbil.data.*;
import com.ple.jerbil.data.bridge.MariadbR2dbcBridge;
import com.ple.jerbil.data.query.CompleteQuery;
import com.ple.jerbil.data.query.SelectQuery;
import com.ple.jerbil.data.selectExpression.Agg;
import com.ple.jerbil.data.selectExpression.AliasedExpression;
import com.ple.jerbil.testcommon.*;
import org.junit.jupiter.api.Test;

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
  final OrderTableContainer sortOrder = testDb.sortOrder;

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
    final IList<Index> indexes = testDb.sortOrder.indexes;
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
    final SelectQuery base = user.select(user.userId);
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
    final CompleteQuery q = item.where(item.type.eq(ItemType.weapon)).selectAll();
    assertEquals("""
        select *
        from item
        where type = 'weapon'
        """, q.toSql());
  }

  @Test
  void testLimitOffset() {
    final CompleteQuery q = player.select().limit(5, 10);
    assertEquals("""
        select *
        from player
        limit 5, 10
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

  @Test
  void testGroupBy() {
    final CompleteQuery q = item.select(item.type, Agg.count.as("total")).groupBy(item.type);
    assertEquals("""
        select type, count(*) as total
        from item
        group by type
        """, q.toSql());
  }

  //TODO: Figure out how to make it preserve 0 after decimal point.
  @Test
  void testHaving() {
    final AliasedExpression total = Agg.sum(item.price).as("total");
    final CompleteQuery q = item.select(item.name, total)
//        .where(total.gt(make(100.00)))  //It is good if this fails to compile because .where() cannot accept a aliasedExpression since that is a post-query value.
        .groupBy(item.name)
        .having(total.gt(make(100.00)));
    assertEquals("""
        select name, sum(price) as total
        from item
        group by name
        having total > 100.0
        """, q.toSql());
  }

  @Test
  void testRollup() {
    //TODO: Implement this.
  }

  //TODO: Add functionality for any_value() and only_full_group_by to work between Mariadb or MySql. If MariaDb, use workaround, if Mysql use any_value().
  @Test
  void testOrderBy() {
    final AliasedExpression total = Agg.sum(item.price).as("total");
    final CompleteQuery q = item.select(item.name, total)
        .where(item.price.ge(make(2.32)))
        .groupBy(item.name)
        .orderBy(total, SortOrder.descending);         //single-expression orderBy descending. Allows AliasedExpressions.
//    .orderBy(item.price);                             //Single-expression orderBy ascending by default.
//    .orderBy(Order.descending, item.price, item.name);  //multi-expression orderBy all descending.
//    .orderBy(IArrayMap.make(item.price, Order.descending, item.name, Order.ascending)); //multi-expression orderBy each expression with specific sortOrder.
//    .orderBy(item.price, item.name);                    //multi-expression orderBy ascending by default.
    assertEquals("""
        select name, sum(price) as total
        from item
        where price >= 2.32
        group by name
        sortOrder by total desc
        """, q.toSql());
  }

  // Here is interesting reason why we use finalized = 1 or <> 1 instead of 'finalized is true' or 'is not true': https://stackoverflow.com/questions/24800881/mysql-true-vs-is-true-on-boolean-when-is-it-advisable-to-use-which-one
  //FIXME: Need to implement dictionary to put backticks around reserved words.
  @Test
  void testSelectDistinct() {
    final CompleteQuery q = sortOrder.selectDistinct(sortOrder.total).where(sortOrder.finalized.isTrue())
        .union(sortOrder.selectDistinct(sortOrder.total).where(sortOrder.finalized.isFalse()));
    assertEquals("""
        select distinct total
        from sortOrder
        where finalized = 1
        union
        select distinct total
        from sortOrder
        where finalized <> 1
        """, q.toSql());
  }

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
  }

  @Test
  void testBetween() {
    final CompleteQuery q = user.select(user.userId, user.name).where(user.userId.isBetween(make(4), make(10)))
        .union(user.select(user.userId, user.name).where(user.userId.isNotBetween(make(4), make(10))));
    assertEquals("""
        select userId, name
        from user
        where userId between 4 and 10
        union
        select userId, name
        from user
        where userId not between 4 and 10
        """, q.toSql());
  }

  @Test
  void testLike() {
    final CompleteQuery q = user.select(user.userId, user.name).where(user.name.isLike(make("%oh%")))
        .unionAll(user.select(user.userId, user.name).where(user.name.isNotLike(make("%oh%"))));
    assertEquals("""
        select userId, name
        from user
        where name like '%oh%'
        union all
        select userId, name
        from user
        where name not like '%oh%'
        """, q.toSql());
  }

  @Test
  void testMatchFullText() {
    final CompleteQuery q = sortOrder.select(sortOrder.phrase).where(sortOrder.phrase.match(make("Hello there")));
    assertEquals("""
        select phrase
        from sortOrder
        where match(phrase) against('Hello there')
        """, q.toSql());
  }

  @Test
  void testExplain() {
    final CompleteQuery q1 = sortOrder.select().explain();
    final CompleteQuery q2 = sortOrder.explain().select();
    assertEquals("""
        explain select *
        from sortOrder
        """, q1.toSql());
    assertEquals("""
        explain select *
        from sortOrder
        """, q2.toSql());
  }

  @Test
  void testAnalyze() { //For mysqlbridge it would have to do explain analyze select, but for mariadbbridge it would just do analyze select.
    final CompleteQuery q1 = sortOrder.select().analyze();
    final CompleteQuery q2 = sortOrder.analyze().select();
    assertEquals("""
        analyze select *
        from sortOrder
        """, q1.toSql());
    assertEquals("""
        analyze select *
        from sortOrder
        """, q2.toSql());
  }

}
