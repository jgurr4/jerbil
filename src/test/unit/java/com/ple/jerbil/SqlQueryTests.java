package com.ple.jerbil;

import com.ple.jerbil.testcommon.*;
import org.junit.jupiter.api.Test;

import static com.ple.jerbil.Literal.from;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SqlQueryTests {

  final UserTable user = new UserTable();
  final PlayerTable player = new PlayerTable();
  final ItemTable item = new ItemTable();
  final InventoryTable inventory = new InventoryTable();

  final Database testDb = Database.from("test").add(user, player, item, inventory);

  @Test
  void testSelect() {

    final Query q = user.where(user.name.eq("john")).select(user.userId);
    assertEquals(q.toSql(), """
      select userId 
      from user 
      where name='john'
      """); // tosql should fail if they haven't passed in what database language for the bridge/translator to use.
    // Because default it's null. But it will pass if they already set the global. I'll test both ways.

  }

  @Test
  void testReusableQueryBase() {

    final Query base = user.select(user.userId);
    final Query q1 = base.where(user.name.eq("john"));
    final Query q2 = base.where(user.name.eq("james"));

    assertEquals(q1.toSql(), """
      select userId 
      from user 
      where name='john'
      """);

    assertEquals(q2.toSql(), """
      select userId 
      from user 
      where name='james'
      """);

  }

  @Test
  void testSelectEnum() {

    final Query q = item.where(item.type.eq(ItemType.weapon.toString()));
    assertEquals(q.toSql(), """
      select * 
      from item 
      where type = 'weapon'
      """);

  }

  @Test
  void testSelectJoins() {

    final Query q = player.join(inventory, item).where(player.name.eq("bob")).and(item.name.eq("sword"));
    assertEquals(q.toSql(), """
      select *
      from player
      inner join inventory using (playerId)
      inner join item using (itemId)
      where player.name='bob' and item.name='sword'
      """);

  }

  @Test
  void testAggregation() {

    final Query q = item.select(Agg.count);
    assertEquals(q.toSql(), """
      select count(*)
      from item
      """);

  }

  @Test
  void testGroupBy() {

    final Query q = item.select(item.type.as("type"), Agg.count.as("total")).groupBy(item.type);
    assertEquals(q.toSql(), """
      select item.type as type, count(*) as total
      from item
      group by item.type
      """);

  }

  @Test
  void testComplexExpressions() {

    final Query q = item
      .select(item.price.times(from(42)).minus(from(1)).times(from(3)).plus(from(1)).as("adjustedPrice"))
      .where(item.price.dividedBy(from(4)).isGreaterThan(from(5)))
      ;
    assertEquals(q.toSql(), """
      select (price * 42 - 1) * (3 + 1) as adjustedPrice
      from item
      where price / 4 > 5
      """);

  }

}
