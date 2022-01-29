package com.ple.jerbil;

import com.ple.jerbil.data.DataGlobal;
import com.ple.jerbil.data.Database;
import com.ple.jerbil.data.bridge.MariadbR2dbcBridge;
import com.ple.jerbil.data.query.CompleteQuery;
import com.ple.jerbil.data.query.QueryList;
import com.ple.jerbil.data.selectExpression.Column;
import com.ple.jerbil.data.selectExpression.NumericExpression.NumericColumn;
import com.ple.jerbil.testcommon.*;
import org.junit.jupiter.api.Test;

import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SqlStructureTests {

  final UserTable user = new UserTable();
  final UserTableColumns userColumns = new UserTableColumns(user);
  final PlayerTable player = new PlayerTable();
  final PlayerTableColumns playerColumns = new PlayerTableColumns(player);
  final ItemTable item = new ItemTable();
  final ItemTableColumns itemColumns = new ItemTableColumns(item);
  final InventoryTable inventory = new InventoryTable();
  final InventoryTableColumns inventoryColumns = new InventoryTableColumns(inventory);
  final Database testDb = Database.make("test").add(user, player, item, inventory);

  public SqlStructureTests() {
    final Properties props = ConfigProps.getProperties();
    DataGlobal.bridge = MariadbR2dbcBridge.make(props.getProperty("driver"), props.getProperty("host"), Integer.parseInt(props.getProperty("port")), props.getProperty("user"), props.getProperty("password"));
  }

  @Test
  void testDatabaseCreateAll() {
    final QueryList<CompleteQuery> testCreateAll = testDb.createAll();
    assertEquals("""
      create database test;
      use test;
      create table user (
        userId int auto_increment,
        name varchar(255) not null,
        age int not null,
        primary key (userId),
        key nm_idx (name)
      ) ENGINE=Aria;
      create table player (
        playerId int auto_increment,
        userId int not null,
        name varchar(20) not null,
        primary key (playerId)
      ) ENGINE=Innodb;
      create table item (
        itemId int auto_increment,
        name varchar(20) not null,
        type enum('weapon','armor','shield','accessory') not null,
        price int not null,
        primary key (itemId),
        key nm_idx (name)
      ) ENGINE=Aria;
      create table inventory (
        playerId int,
        itemId int,
        primary key (playerId,itemId)
      ) ENGINE=Aria;
      """, testCreateAll.toSql());
  }

  @Test
  void testAddColumn() {
    final CompleteQuery q2 = item.create();
    Column newColumn = Column.make("quantity").asInt().indexed();
    item.add(newColumn);
    final CompleteQuery q = item.create();
    assertEquals("""
      create table item (
        itemId int auto_increment,
        name varchar(20) not null,
        type enum('weapon','armor','shield','accessory') not null,
        price int not null,
        quantity int not null,
        primary key (itemId),
        key nm_qnty_idx (name,quantity)
      ) ENGINE=Aria
      """, q.toSql());
    //FIXME: This should generate the name based on the multi-column names. Ex: key name_product_idx  limit to 7 characters. if limit is reached remove vowels. so it becomes: key nm_prdct_idx
  }



  /*
    @Test
    void testModifyColumn() {
      Column newColumn = Column.make("quantity", item).integer(5); //When translator sees that the column is not
      // the same as what exists in mysql table already, then this will alter table, and modify the column.
    }

    @Test
    void testChangeColumn() {
      //The translator must notice that if a existing column is removed from original code, and it is replaced with a new column
      // of a different name and possibly different data type, then the translator should attempt to rename the column, rather
      // than dropping the column and losing data.
      item.remove(itemColumns.name).set(Column.make("userName", item).indexed().varchar());
      // the column is being recreated. This means the translator must change the column rather than dropping it.
    }
// Make a test to test ability to make multi-index keys not primary.

  */
  @Test
  void testGeneratedColumn() {
    NumericColumn quantity = Column.make("quantity").asInt();
    item.add(quantity);
    Column total = Column.make("total").asDecimal(14, 2).generatedFrom(itemColumns.price.times(quantity));
    item.add(total);
    final CompleteQuery q = item.create();
    assertEquals("""
      create table item (
        itemId int auto_increment,
        name varchar(20) not null,
        type enum('weapon','armor','shield','accessory') not null,
        price int not null,
        quantity int not null,
        total decimal(14, 2) as (price * quantity),
        primary key (itemId),
        key nm_idx (name)
      ) ENGINE=Aria
      """, q.toSql());
  }

}