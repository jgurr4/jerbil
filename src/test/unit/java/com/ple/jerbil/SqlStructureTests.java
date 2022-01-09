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
      create table user (
        userId int primary key auto_increment,
        name varchar(255) not null,
        age int not null,
        key (name)
      ) ENGINE=Aria;
      create table player (
        playerId int primary key auto_increment,
        userId int not null,
        name varchar(20) not null
      ) ENGINE=Innodb;
      create table item (
        itemId int primary key auto_increment,
        name varchar(20) not null,
        type enum('weapon','armor','shield','accessory') not null,
        price int not null,
        key (name)
      ) ENGINE=Aria;
      create table inventory (
        playerId int,
        itemId int,
        primary key (playerId, itemId)
      ) ENGINE=Aria;
      """, testCreateAll.toSql());
  }

  @Test
  void testAddColumn() {
    final CompleteQuery q2 = item.create();
    Column newColumn = Column.make("quantity", item).asInt();
    item.add(newColumn);
    final CompleteQuery q = item.create();
    assertEquals("""
      create table item (
        itemId int primary key auto_increment,
        name varchar(20) not null,
        type enum('weapon','armor','shield','accessory') not null,
        price int not null,
        quantity int not null,
        key (name)
      ) ENGINE=Aria
      """, q.toSql());
//      ResultSet rs = q.execute().getResultSet(); // For this we will be using rxjava over jdbc to make asynchronous
    // connections and streams of resultset data.
    //When translator sees that this column doesn't
    // already exist in the item table inside mysql when it communicates to mysql, it will create this column using the add
    // column command, rather than dropping table or creating a new table, because we don't want users to lose their
    // data. It should never auto-drop or create new tables unless the table doesn't exist.
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

  */
  @Test
  void testGeneratedColumn() {
    NumericColumn quantity = Column.make("quantity", item).asInt();
    item.add(quantity);
    Column total = Column.make("total", item).asDecimal(14, 2).generatedFrom(itemColumns.price.times(quantity));
    item.add(total);
    final CompleteQuery q = item.create();
    assertEquals("""
      create table item (
        itemId int primary key auto_increment,
        name varchar(20) not null,
        type enum('weapon','armor','shield','accessory') not null,
        price int not null,
        quantity int not null,
        total decimal(14, 2) as (price * quantity),
        key (name)
      ) ENGINE=Aria
      """, q.toSql());
  }

}