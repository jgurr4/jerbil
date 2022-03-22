package com.ple.jerbil.functional;

import com.ple.jerbil.data.*;
import com.ple.jerbil.data.bridge.MariadbR2dbcBridge;
import com.ple.jerbil.data.query.Table;
import com.ple.jerbil.data.query.TableContainer;
import com.ple.jerbil.data.reactiveUtils.ReactiveMono;
import com.ple.jerbil.data.reactiveUtils.ReactiveWrapper;
import com.ple.jerbil.data.selectExpression.Column;
import com.ple.jerbil.data.selectExpression.NumericExpression.NumericColumn;
import com.ple.jerbil.data.selectExpression.StringColumn;
import com.ple.jerbil.data.sync.*;
import com.ple.jerbil.testcommon.*;
import com.ple.util.IArrayList;
import com.ple.util.IArrayMap;
import com.ple.util.IList;
import com.ple.util.IMap;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import reactor.core.publisher.Hooks;

import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;

public class BridgeTests {
  final TestDatabaseContainer testDb = DatabaseBuilder.generate(TestDatabaseContainer.class, "test");
  final UserTableContainer user = testDb.user;
  final ItemTableContainer item = testDb.item;
  final PlayerTableContainer player = testDb.player;
  final InventoryTableContainer inventory = testDb.inventory;
  final OrderTableContainer order = testDb.order;

  public BridgeTests() {
    final Properties props = ConfigProps.getProperties();
    DataGlobal.bridge = MariadbR2dbcBridge.make(props.getProperty("host"), Integer.parseInt(props.getProperty("port")),
        props.getProperty("user"), props.getProperty("password")
    );
  }

  @Test
  void testExecute() {
    Hooks.onOperatorDebug();
    final IList<DbResult> dbResults = DataGlobal.bridge.execute("select 1, 2 union all select 3, 4")
        .unwrapList();
    final DbResult dbResult = dbResults.get(0);
    System.out.println(dbResult.result);
    assertEquals(IArrayList.make("1", "2"), dbResult.result.columnNames);
    assertArrayEquals(new Object[]{1,2,3,4}, dbResult.result.values);
    assertEquals(0, dbResult.rowsUpdated);
    assertEquals(IArrayList.empty, dbResult.error);
    assertEquals(IArrayList.empty, dbResult.warning);
  }

  @Test
  void testGetDb() {
    Hooks.onOperatorDebug();
    DatabaseContainer test = DatabaseContainer.getDbContainer("test").unwrap();
    if (test.equals(DatabaseContainer.empty)) {
      DataGlobal.bridge.execute(testDb.createAll().toSql()).unwrap();
      test = DatabaseContainer.getDbContainer("test").unwrapMono().checkpoint().block();
    }
    assertEquals("test", test.database.databaseName);
    assertEquals(item, test.tables.get("item"));
  }

  @Test
  void testDiffToSql() {
    // TODO: Change "user" to "useer" once we implement ID comment checking, to test renaming table.
    // TODO: Once we implement the comment system with id, we'll be able to allow renaming columns and tables which will automatically
    //  migrate the data successfully. Until then we have to simply add and drop columns/tables.
    final Table userTable = Table.make("user", testDb.database);
    final NumericColumn userId = Column.make("userId", userTable).asInt();
    final StringColumn name = Column.make("name", userTable).asVarchar(15);
    final NumericColumn age = Column.make("agee", userTable).asInt();
    final IMap<String, Index> indexSpecs = IArrayMap.make("nm_idx", Index.make(IndexType.secondary, "nm_idx",
        userTable, IndexedColumn.make(name, 10, null)));
    final IMap<String, Column> columns = IArrayMap.make(userId.columnName, userId, name.columnName, name,
        age.columnName, age);
    final TableContainer updatedUser = TableContainer.make(userTable, columns, StorageEngine.simple, indexSpecs, userId);
    final DatabaseContainer test = DatabaseContainer.make(testDb.database, IArrayMap.make(testDb.inventory.tableName,
        testDb.inventory, testDb.item.tableName, testDb.item, testDb.order.tableName, testDb.order, updatedUser.table.tableName, updatedUser));
    final DbDiff dbDiff = DiffService.compareDatabases(testDb, test);
    assertEquals("""
        use test;
        create table player (
          playerId int(11) auto_increment,
          userId int(11) not null,
          name varchar(20) not null,
          primary key (playerId)
        ) ENGINE=Innodb;
        alter table user add column age int(11) not null;
        alter table user drop column agee;
        alter table user change userId userId bigint(20) auto_increment primary key;
        alter table user change name name varchar(255) not null;
        alter table user add primary key if not exists (userId);
        alter table user drop index nm_idx;
        alter table user add index nm_idx (name);
        """, dbDiff.toSql());
  }

  @Test
  void testDiffToSqlWithFilter() {
    final Table userTable = Table.make("user", testDb.database);
    final NumericColumn userId = Column.make("userId", userTable).asInt();
    final StringColumn name = Column.make("name", userTable).asVarchar(15);
    final NumericColumn age = Column.make("agee", userTable).asInt();
    final IMap<String, Index> indexSpecs = IArrayMap.make("nm_idx", Index.make(IndexType.secondary, "nm_idx",
        userTable, IndexedColumn.make(name, 10, null)));
    final IMap<String, Column> columns = IArrayMap.make(userId.columnName, userId, name.columnName, name,
        age.columnName, age);
    final TableContainer updatedUser = TableContainer.make(userTable, columns, StorageEngine.simple, indexSpecs, userId);
    final DatabaseContainer test = DatabaseContainer.make(testDb.database, IArrayMap.make(testDb.inventory.tableName,
        testDb.inventory, testDb.item.tableName, testDb.item, testDb.order.tableName, testDb.order, updatedUser.table.tableName, updatedUser));
    final DbDiff dbDiff = DiffService.compareDatabases(testDb, test);
    final DbDiff createUpdate = dbDiff.filter(DdlOption.make().create().update());
    final DbDiff deleteUpdate = dbDiff.filter(DdlOption.make().delete().update());  //TODO: Make update() implied by default for delete and create, so it goes deeper into each table and column for delete
    final DbDiff update = dbDiff.filter(DdlOption.make().update());
    assertEquals("""
        use test;
        create table player (
          playerId int(11) auto_increment,
          userId int(11) not null,
          name varchar(20) not null,
          primary key (playerId)
        ) ENGINE=Innodb;
        alter table user add column age int(11) not null;
        alter table user change userId userId bigint(20) auto_increment primary key;
        alter table user change name name varchar(255) not null;
        alter table user add primary key if not exists (userId);
        alter table user drop index nm_idx;
        alter table user add index nm_idx (name);
        """, createUpdate.toSql());
    assertEquals("""
        use test;
        alter table user drop column agee;
        alter table user change userId userId bigint(20) auto_increment primary key;
        alter table user change name name varchar(255) not null;
        alter table user drop index nm_idx;
        alter table user add index nm_idx (name);
        """, deleteUpdate.toSql());
    assertEquals("""
        use test;
        alter table user change userId userId bigint(20) auto_increment primary key;
        alter table user change name name varchar(255) not null;
        alter table user drop index nm_idx;
        alter table user add index nm_idx (name);
        """, update.toSql());
  }

  @Test
  void syncCreateWithDbMissing() {
    DataGlobal.bridge.execute(testDb.drop()).unwrapFlux().blockLast();
    final SyncResult syncResult = testDb.sync(DdlOption.make().create()).unwrap();
    System.out.println(syncResult.diff);
    System.out.println(((DbDiff) syncResult.diff).toSql());
    syncResult.result.unwrapFlux().blockLast();
    assertNotNull(((DbDiff) syncResult.diff).databaseName);
    assertEquals(5, ((DbDiff) syncResult.diff).tables.create.size());
  }

  @Test
  void syncWithModifiedColumn() {
    DataGlobal.bridge.execute("use test; alter table player modify column name varchar(50) not null").unwrapFlux().blockLast();
    //TODO: Replace sql with jerbil methods like player.modify(playerColumns.name).asVarchar(50);  or player.alter also add support for player.rename player.change
//    final ReactiveWrapper<SyncResult> syncResult = testDb.sync(DdlOption.make().create().update().delete());
//    final DbDiff diff = (DbDiff) syncResult.unwrap().diff;
//    for (Object o : syncResult.unwrap().result.unwrapList()) {
//      o = (DbResult) o;
//    }
    final DbDiff diff = DiffService.compareDatabases(testDb, DataGlobal.bridge.getDb("test").unwrap());
    System.out.println(diff);
    System.out.println(diff.toSql());
    assertEquals(1 , diff.tables.update.size());
//    assertEquals(1, ((DbDiff) syncResult.diff.unwrap()).tables.update.length());
//    assertEquals(1, syncResult.diff.unwrap().getDiffs().size);
  }


  /*
  @Test
  void syncWithoutConflicts() { //It should reuse existing with no diffs.
    DataGlobal.bridge.execute(testDb.drop()).unwrapFlux().blockLast();
    DataGlobal.bridge.execute(testDb.createAll().toSql()).unwrapFlux().blockLast();
    final DdlOption ddlOption = DdlOption.make().create().update().delete();
    final ReactiveWrapper<SyncResult> syncResult = testDb.sync(ddlOption);
    assertEquals(0, syncResult.unwrap().diff.getTotalDiffs()); //FIXME: Find a way to run methods through Diff interface
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
   */
}
