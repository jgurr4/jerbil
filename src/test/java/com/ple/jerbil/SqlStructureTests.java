package com.ple.jerbil;

import com.ple.jerbil.data.DataGlobal;
import com.ple.jerbil.data.DatabaseBuilder;
import com.ple.jerbil.data.bridge.MariadbR2dbcBridge;
import com.ple.jerbil.data.query.CompleteQuery;
import com.ple.jerbil.data.query.CreateQuery;
import com.ple.jerbil.data.query.QueryList;
import com.ple.jerbil.data.query.TableContainer;
import com.ple.jerbil.data.selectExpression.Column;
import com.ple.jerbil.data.selectExpression.NumericExpression.NumericColumn;
import com.ple.jerbil.testcommon.*;
import org.junit.jupiter.api.Test;

import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SqlStructureTests {

  final TestDatabaseContainer testDb = DatabaseBuilder.generate(TestDatabaseContainer.class, "test");
  final UserTableContainer user = testDb.user;
  final ItemTableContainer item = testDb.item;
  final PlayerTableContainer player = testDb.player;
  final InventoryTableContainer inventory = testDb.inventory;
  final OrderTableContainer order = testDb.order;

  public SqlStructureTests() {
    final Properties props = ConfigProps.getProperties();
    DataGlobal.bridge = MariadbR2dbcBridge.make(props.getProperty("driver"), props.getProperty("host"),
        Integer.parseInt(props.getProperty("port")), props.getProperty("user"), props.getProperty("password"));
  }

  @Test
  void testCreateOrderTable() {
    final CreateQuery q = order.create();
    assertEquals("""
        create table `order` (
          orderId bigint unsigned auto_increment,
          `add` varchar(255) default ('barter') unique,
          phrase text,
          userId int unsigned not null,
          itemId int(10) unsigned not null,
          scale mediumint unsigned not null,
          quantity smallint unsigned not null,
          price decimal(14, 2) not null,
          total decimal(14, 2) as (quantity * price),
          finalized boolean not null,
          myDouble double not null,
          myFloat float not null,
          mySet set('weapon','armor','shield','accessory') default ('weapon'),
          saleDate date not null,
          saleTime time not null,
          saleDateTime datetime default current_timestamp on update current_timestamp,
          myInvis int invisible,
          key usrd_itmd_idx (userId,itemId),
          primary key (orderId),
          fulltext index phrs_idx (phrase)
        ) ENGINE=Aria
        """, q.toSql());
  }

  @Test
  void testDatabaseCreateAll() {
    final QueryList<CompleteQuery> testCreateAll = testDb.createAll();
    assertEquals("""
        create database test;
        use test;
        create table user (
          userId bigint auto_increment,
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
        create table `order` (
          orderId bigint unsigned auto_increment,
          `add` varchar(255) default ('barter') unique,
          phrase text,
          userId int unsigned not null,
          itemId int(10) unsigned not null,
          scale mediumint unsigned not null,
          quantity smallint unsigned not null,
          price decimal(14, 2) not null,
          total decimal(14, 2) as (quantity * price),
          finalized boolean not null,
          myDouble double not null,
          myFloat float not null,
          mySet set('weapon','armor','shield','accessory') default ('weapon'),
          saleDate date not null,
          saleTime time not null,
          saleDateTime datetime default current_timestamp on update current_timestamp,
          myInvis int invisible,
          key usrd_itmd_idx (userId,itemId),
          primary key (orderId),
          fulltext index phrs_idx (phrase)
        ) ENGINE=Aria;
        """, testCreateAll.toSql());
  }

  @Test
  void testAddColumn() {
    Column newColumn = Column.make("quantity", item.table).asInt().indexed();
    TableContainer newTable = item.add(newColumn);
    final CompleteQuery q = newTable.create();
    assertEquals("""
        create table item (
          itemId int auto_increment,
          name varchar(20) not null,
          type enum('weapon','armor','shield','accessory') not null,
          price int not null,
          quantity int not null,
          primary key (itemId),
          key nm_idx (name),
          key qnty_idx (quantity)
        ) ENGINE=Aria
        """, q.toSql());
  }

  @Test
  void testRemoveColumn() {

  }

  /*
    @Test
    void dropDatabase() {

    }

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
    NumericColumn quantity = Column.make("quantity", item.table).asInt();
    NumericColumn total = Column.make("total", item.table).asDecimal(14, 2).generatedFrom(item.price.times(quantity));
    TableContainer newTable = item.add(quantity, total);
    final CompleteQuery q = newTable.create();
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

  /*
  //ALTER TABLE tests:
  @Test
  void testModifyColumn() {
  }

  @Test
  void testRenameColumn() {
  }

  @Test
  void testChangeColumn() {
  }

  @Test
  void dropColumn() {
  }

  @Test
  void addColumn() {
  }

  // Database objects tests:
  @Test
  void dropTable() {
  }

  @Test
  void dropDatabase() {
  }

  @Test
  void testCreateTableLike() {
  }

  @Test
  void testCreateTempTable() {
    // Temporary tables are useful since they only last as long as the session. So it can be perfect for
    // developers who want everything to drop after done running their tests. Especially since a connection/session
    // cannot view temporary tables created outside of them. So multiple connections can use the same name for a table
    // at the same time and it won't cause any conflicts.
  }

  @Test
  void testCreateView() {
  }

  @Test
  void testCreateProcedure() {
  }

  @Test
  void testCreateFunction() {
  }

  @Test
  void testCreateTrigger() {
  }

  @Test
  void testCreateEvent() {
  }

  @Test
  void showTables() {
  }

  @Test
  void showDatabases() {
  }

  @Test
  void showCreateTable() {
  }

  @Test
  void dropProcedure() {
  }

  @Test
  void dropFunction() {
  }

  @Test
  void dropView() {
  }

  @Test
  void dropEvent() {
  }

  @Test
  void dropTrigger() {
  }

  @Test
  void dropIndex() {
  }

  @Test
  void createIndex() {
  }

  @Test
  void testIfExists() { // drop table if exists.
  }

  @Test
  void testIfNotExists() {  // create table if not exists.
  }

   */
}