package com.ple.jerbil;

import com.ple.jerbil.sql.Database;
import com.ple.jerbil.sql.selectExpression.Column;
import com.ple.jerbil.sql.query.QueryList;
import com.ple.jerbil.testcommon.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

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

  @BeforeAll
  @Test
  void testDatabaseCreateAll() {
    final QueryList testCreateAll = testDb.createAll();
    assertEquals(testCreateAll.toSql(), """
       create database test;
       create table user ( 
       userId int primary key auto_increment,
       name varchar(20),
       key (name)); 
       create table player (
       playerId int primary key auto_increment,
       userId int,
       name varchar(20));
       create table item (
       itemId int primary key auto_increment,
       name varchar(255),
       type enum('weapon','armor','shield','accessory'),
       price int,
       `add` int ));
       create table inventory (
       playerId int,
       itemId int ));""");
  }

  @Test
  void testAddColumn() {
    Column newColumn = Column.make("quantity", item).integer(); //When translator sees that this column doesn't
    // already exist in the table inside mysql when it communicates to mysql, it will create this column using the add
    // column command, rather than dropping table or creating a new table, because we don't want users to lose their
    // data. It should never auto-drop or create new tables unless the table doesn't exist.
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

}
