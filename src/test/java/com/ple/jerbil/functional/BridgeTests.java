package com.ple.jerbil.functional;

import com.ple.jerbil.data.*;
import com.ple.jerbil.data.reactiveUtils.ReactiveWrapper;
import com.ple.jerbil.data.bridge.MariadbR2dbcBridge;
import com.ple.jerbil.data.sync.DbDiff;
import com.ple.jerbil.data.sync.DdlOption;
import com.ple.jerbil.data.sync.Diff;
import com.ple.jerbil.data.sync.SyncResult;
import com.ple.jerbil.testcommon.*;
import com.ple.util.IArrayList;
import com.ple.util.IList;
import io.r2dbc.spi.Result;
import org.junit.jupiter.api.Test;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import reactor.core.publisher.Hooks;
import reactor.core.publisher.Mono;

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
/* uncomment to see example of how result and row.get works
    (Mono.just((MariadbR2dbcBridge) DataGlobal.bridge))
        .flatMapMany(bridge -> bridge.pool.create())
        .map(conn -> conn.createStatement("use test; select 1, 2 union all select 3, 4; select 5, 6 union all select 7, 8"))
//        .log()
        .flatMap(stmt -> stmt.execute())
        .log()
        .flatMap(result -> result.map((row, rowMetadata) -> (Integer) row.get(0)))
        .doOnNext(firstColumnOfResult -> System.out.println(firstColumnOfResult))
        .blockLast();
*/
//    final ReactiveWrapper<DbResult> execute = DataGlobal.bridge.execute("select 1; select 2");
//    final IList<ITable> resultList = execute.unwrap().resultList;
//    System.out.println(resultList);
  }

  @Test
  void testGetDb() {
    Hooks.onOperatorDebug();
    final ReactiveWrapper<DatabaseContainer> test = DatabaseContainer.getDbContainer("test");
    assertEquals("test", test.log().unwrap().database.databaseName);
    System.out.println(testDb.tables.toString().replaceAll("Table\\{", "\nTable{"));
    System.out.println(test.toString().replaceAll("Table\\{", "\nTable{"));
    assertTrue(test.unwrap().database.databaseName.equals("test"));
    assertTrue(test.unwrap().tables.get("item").equals(item.table));
  }

  @Test
  void syncCreateWithDbMissing() { //Should create database using Database Object. Every diff should exist because it is forced to create db for first time.
    DataGlobal.bridge.execute(testDb.drop()).unwrapFlux().blockLast();
    final DdlOption ddlOption = DdlOption.make().create();
    final SyncResult<Diff<Database>> syncResult = testDb.sync(ddlOption);
    assertNotNull(((DbDiff) syncResult.diff.unwrap()).databaseName);
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
//    assertEquals(1, ((DbDiff) syncResult.diff.unwrap()).tables.update.length());
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
