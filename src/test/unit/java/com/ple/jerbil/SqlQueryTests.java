package com.ple.jerbil;

import com.ple.jerbil.testcommon.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SqlQueryTests {

  final UserTable user = new UserTable();
  final PlayerTable player = new PlayerTable();
  final ItemTable item = new ItemTable();
  final InventoryTable inventory = new InventoryTable();

  final Database testDb = Database.make("test").add(user, player, item, inventory);

  @Test
  void testSelect() {

    final Query q = user.where(user.name.eq("john")).select(user.userId);
    assertEquals(q.toString(), """
      select userId 
      from user 
      where name='john'
      """);

  }

  @Test
  void testReusableQueryBase() {

    final Query base = user.select(user.userId);
    final Query q1 = base.where(user.name.eq("john"));
    final Query q2 = base.where(user.name.eq("james"));


    assertEquals(q1.toString(), """
      select userId 
      from user 
      where name='john'
      """);

    assertEquals(q2.toString(), """
      select userId 
      from user 
      where name='james'
      """);

  }

  @Test
  void testSelectEnum() {

    final Query q = item.where(item.type.eq(ItemType.weapon));
    assertEquals(q.toString(), """
      select * 
      from item 
      where type = 'weapon'
      """);

  }

  @Test
  void testSelectJoins() {

    final Query q = player.join(inventory, item).where(player.name.eq("bob")).and(item.name.eq("sword")));
    assertEquals(q.toString(), """
      select *
      from player
      inner join inventory using (playerId)
      inner join item using (itemId)
      where player.name='bob' and item.name='sword'
      """);

  }

  @Test
  void testAggregation() {

    final Query q = item.select(count);
    assertEquals(q.toString(), """
      select count(*)
      from item
      """);

  }

  @Test
  void testGroupBy() {

    final Query q = item.select(item.type.as("type"), count.as("total")), groupBy (item.type);
    assertEquals(q.toString(), """
      select item.type as type, count(*) as total
      from item
      group by item.type
      """);

  }

}
