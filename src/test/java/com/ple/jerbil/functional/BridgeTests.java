package com.ple.jerbil.functional;

import com.ple.jerbil.data.*;
import com.ple.jerbil.data.bridge.MariadbR2dbcBridge;
import com.ple.jerbil.data.bridge.ReactiveWrapper;
import com.ple.jerbil.data.bridge.SynchronousObject;
import com.ple.jerbil.data.query.Table;
import com.ple.jerbil.data.selectExpression.Column;
import com.ple.jerbil.data.sync.*;
import com.ple.jerbil.testcommon.*;
import com.ple.util.IArrayList;
import com.ple.util.IHashMap;
import com.ple.util.IMap;
import org.junit.jupiter.api.Test;

import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;

public class BridgeTests {

  final UserTable user = new UserTable();
  final UserTableColumns userColumns = new UserTableColumns(user);
  final PlayerTable player = new PlayerTable();
  final PlayerTableColumns playerColumns = new PlayerTableColumns(player);
  final ItemTable item = new ItemTable();
  final ItemTableColumns itemColumns = new ItemTableColumns(item);
  final InventoryTable inventory = new InventoryTable();
  final InventoryTableColumns inventoryColumns = new InventoryTableColumns(inventory);
  final Database testDb = Database.make("test").add(user, player, item, inventory);

  public BridgeTests() {
    final Properties props = ConfigProps.getProperties();
    DataGlobal.bridge = MariadbR2dbcBridge.make(
      props.getProperty("driver"), props.getProperty("host"), Integer.parseInt(props.getProperty("port")),
      props.getProperty("user"), props.getProperty("password")
    );
  }

  @Test
  void testCompareMissingTable() {
    final DbDiff diffs = DiffService.compare(
      testDb.wrap(), new Database("myDb", IArrayList.make(user, player, item)).wrap()).unwrap();
    assertEquals(IArrayList.make("inventory"), diffs.tables.create);
    assertNull(diffs.tables.delete);
    assertNull(diffs.tables.update);
    System.out.println(diffs.tables.create);
  }

  @Test
  void testDbDiffFilter() {
    Table alteredItem = Table.make(
      StorageEngine.transactional, "item", IArrayList.make(
        itemColumns.itemId, itemColumns.name,
        itemColumns.type, Column.make("price").asInt()
      ));
    Table extraTable = Table.make(StorageEngine.simple, "extra", IArrayList.make(Column.make("id").bigId()));
    final DbDiff diff = DiffService.compare(
      testDb.wrap(), Database.make("myDb", IArrayList.make(user, player, alteredItem, extraTable)).wrap()).unwrap();  //inventory needs create, alteredItem needs update, extraTable needs delete.
    assertEquals(1, diff.filter(DdlOption.make().create()).tables.create.length());
    assertEquals(0, diff.filter(DdlOption.make().create()).tables.delete.length());
    assertEquals(0, diff.filter(DdlOption.make().create()).tables.update.length());
    assertEquals(0, diff.filter(DdlOption.make().delete()).tables.create.length());
    assertEquals(1, diff.filter(DdlOption.make().delete()).tables.delete.length());
    assertEquals(0, diff.filter(DdlOption.make().delete()).tables.update.length());
    assertEquals(0, diff.filter(DdlOption.make().update()).tables.create.length());
    assertEquals(0, diff.filter(DdlOption.make().update()).tables.delete.length());
    assertEquals(1, diff.filter(DdlOption.make().update()).tables.update.length());
    // How to access table properties, column properties and columnDiffs:
    // diff.filter().tables.create.get(0).engine;
    // diff.filter().tables.update.get(0).create.get(0).dataSpec;
    // diff.filter().tables.update.get(0).update.get(0). ;  //TODO: Ask what is inside ColumnDiff.update?
  }


  @Test
  void testGetDb() {
    final Database test = Database.getDb("test").unwrap();
    assertEquals("test", test.name);
    System.out.println(testDb.tables.toString().replaceAll("Table\\{", "\nTable{"));
    System.out.println(test.tables.toString().replaceAll("Table\\{", "\nTable{"));
    assertTrue(test.tables.contains(user));
    assertTrue(test.tables.contains(item));
  }

  @Test
  void syncCreateWithDbMissing() { //Should create database using Database Object. Every diff should exist because it is forced to create db for first time.
    DataGlobal.bridge.execute(testDb.drop()).unwrapFlux().blockLast();
    final DdlOption ddlOption = DdlOption.make().create();
    final SyncResult<Diff<Database>> syncResult = testDb.sync(ddlOption);
    assertNotNull(((DbDiff)syncResult.diff.unwrap()).name);
  }

  @Test
  void syncWithoutConflicts() { //It should reuse existing with no diffs.
    DataGlobal.bridge.execute(testDb.drop()).unwrapFlux().blockLast();
    DataGlobal.bridge.execute(testDb.createAll().toSql()).unwrapFlux().blockLast();
    final DdlOption ddlOption = DdlOption.make().create().update().delete();
    final SyncResult<Diff<Database>> syncResult = testDb.sync(ddlOption);
    assertEquals(0, syncResult.diff.unwrap().getTotalDiffs()); //FIXME: Find a way to run methods through Diff interface
  }

  @Test
  void syncWithConflictingTable() { //update-level diffs in tables exist, so should contain error inside SyncResult.
    DataGlobal.bridge.execute("alter table player modify column name varchar(50) not null").unwrapFlux()
      .blockLast();  //TODO: Replace sql with jerbil methods like player.modify(playerColumns.name).asVarchar(50);  or player.alter also add support for player.rename player.change
    final DdlOption ddlOption = DdlOption.make().create().update().delete();
    final SyncResult<Diff<Database>> syncResult = testDb.sync(ddlOption);
    assertEquals(1, ((DbDiff) syncResult.diff.unwrap()).tables.update.length());
//    assertEquals(1, syncResult.diff.unwrap().getDiffs().size);
  }

  @Test
  void migrateColumn() {
    //Should rename a existing column.
  }

  @Test
  void findRenamedColumn() {
    //Should use column comment or perhaps meta data table with currentTableName and id to locate the correct column and rename it.
    // Id can be generated by hashcode of name. That way jerbil can always know if a name is changed outside of it because the id is not matching the hashcode.
    // If a column is renamed inside jerbil the id is changed as well. But if the column is renamed outside of jerbil, the hashcode won't match it anymore.
  }

  @Test
  void migrateTable() {
    // Should create table with new name and migrate data from old table to new one.
    // If a table or database has been altered outside of jerbil this is what needs to happen:
    // When a user syncs a table they can optionally provide a list of column names that have been changed so that jerbil knows what the new names are for each column.
    // Similarly with database sync they can optionally provide a list of table names that have been changed.
    // This is required because the way jerbil is set up, it uses the name of a Database/Table/Column in order to find the
    // correct object to make changes to. If the name is changed outside of jerbil, jerbil has no way of knowing which object
    // is the correct one anymore. That is why for every sync method users can optionally provide a list of names. Or they can
    // simply change the original names of the objects in their framework. If they do not do this, jerbil will go ahead
    // and create new tables/columns and ignore the renamed ones, (Or in the case of delete permissions, jerbil will
    // delete the old ones. This is why good database practices are important here. Backup db regularly and make sure
    // you do all your changes within jerbil, and any changes that you do outside of jerbil have to be reflected in the
    // jerbil code somewhere.
    // An alternative solution to this is for jerbil to create comments with generated id inside it for each column and table.
    // But that doesn't work for databases. So for databases you might have a meta data table inside it with the database id,
    // or perhaps set a variable inside the database for the id.
  }

  @Test
  void migrateDatabase() {
    //Should create database with new name and migrate data from old database to new one.
  }

  @Test
  void checkDiffsWithoutSync() {
    //Should compare Database object with remote/local server database and output the differences without implementing changes.
  }

  @Test
  void getDbAlterthenSync() {
    //Should obtain an existing database from remote/local server, then alter something about it, and sync it to change what is there.
  }

  @Test
  void syncCreateWithMissingTable() { //Missing table means create should add it.
  }

  @Test
  void syncCreateWithMissingColumn() { //Missing Column means create should add it.
  }

  @Test
  void syncCreateWithConflictingColumn() { //update-level diffs in columns exist, so should contain error inside SyncResult.
  }

  @Test
  void syncUpdateWithTableMissing() { //create-level diff exists in tables, so should contain the error inside SyncResult.
    final DdlOption ddlOption = DdlOption.make().create().update();
  }

  @Test
  void syncUpdateWithExtraTable() { //delete-level diff exists in tables, so should contain the error inside SyncResult.
  }

  @Test
  void syncUpdateWithColumnMissing() { //create-level diff exists in tables, so should contain the error inside SyncResult.
  }

  @Test
  void syncUpdateWithExtraColumn() { //delete-level diff exists in tables, so should contain the error inside SyncResult.
  }

  @Test
  void syncUpdateWithConflictingTable() { //Conflicting table means update should modify/migrate it.
  }

  @Test
  void syncUpdateWithConflictingColumn() { //Conflicting column means update should modify/migrate it.
  }

  @Test
  void syncDeleteWithConflictingTable() { //update-level diffs means delete should contain the error in SyncResult.
    final DdlOption ddlOption = DdlOption.make().create().update().delete();
  }

  @Test
  void syncDeleteWithConflictingColumn() { //update-level diffs means delete should contain the error in SyncResult.
  }

  @Test
  void syncDeleteWithExtraTable() { //Extra table must be dropped.
  }

  @Test
  void syncDeleteWithExtraColumn() { //Extra column must be dropped.
  }

  @Test
  void syncDeleteWithExtraIndex() { //Extra index must be dropped.
  }

  @Test
  void syncDeleteWithExtraProcedure() { //Extra Procedure must be dropped.
  }

}
