package com.ple.jerbil.functional;

import com.ple.jerbil.data.*;
import com.ple.jerbil.data.bridge.MariadbR2dbcBridge;
import com.ple.jerbil.data.sync.DdlOption;
import com.ple.jerbil.testcommon.*;
import org.junit.jupiter.api.Test;

import java.util.Properties;

import static com.ple.jerbil.data.sync.DdlOption.*;

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
    DataGlobal.bridge = MariadbR2dbcBridge.make(props.getProperty("driver"), props.getProperty("host"), Integer.parseInt(props.getProperty("port")), props.getProperty("user"), props.getProperty("password"));
  }

  //if db doesn't exist, all options will create it. If it does exist, all options will create database.
  @Test
  void syncCreateWithDbMissing() { //Should create database using Database Object.
    final DdlOption ddlOption = DdlOption.make().create();
    testDb.sync(ddlOption);
  }
/*

  sync(db1, db2) {
    DbDiff dbDiff = DiffService.compare(db1, db2);
    DbDiff filteredDiff = dbDiff.filter(ddlOption);
    String sql = filteredDiff.toSql();
    bridge.execute(sql);

    compare(db1, db2).filter(ddlOption).toSql().execute();
  }
*/

  @Test
  void syncCreateWithoutConflicts() { //Diffs don't exist in this case so reuse without error.
    final DdlOption ddlOption = DdlOption.make().create();

  }

  @Test
  void syncCreateWithMissingTable() { //Missing table means create should add it.

  }

  @Test
  void syncCreateWithMissingColumn() { //Missing Column means create should add it.

  }

  @Test
  void syncCreateWithConflictingTable() { //update-level diffs in tables exist, so should contain error inside SyncResult.

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

  /*

  @Test
  void syncCreateSchemaGeneratedSuccess() {
    DataGlobal.bridge.execute("drop database if exists test").blockLast();
    final Optional<SyncResult> db = testDb.sync(DdlOption.create).blockOptional();
    if (db.isPresent()) {
      if (db.get().hasError()) {
        System.out.println(db.get().errorMessage);
        fail();
      }
      assertEquals(GeneratedType.generated, db.get().generatedType);
    }
  }

  @Test
  void syncCreateSchemaReusedSuccess() {
    final Optional<SyncResult> db = testDb.sync(DdlOption.create).blockOptional();
    if (db.isPresent()) {
      if (db.get().hasError()) {
        System.out.println(db.get().errorMessage);
        fail();
      }
      assertEquals(GeneratedType.reused, db.get().generatedType);
    }
  }

  @Test
  void syncCreateSchemaFailWithMissingCol() {
    DataGlobal.bridge.execute("use test; alter table player drop column if exists name").blockLast();
    final Optional<SyncResult> sr = testDb.sync(DdlOption.create)
      .filter(SyncResult::hasError).blockOptional();
    if (sr.isPresent()) {
      if (sr.get().hasError()) {
        System.out.println(sr.get().errorMessage);
      } else {
        fail();
      }
      assertEquals(GeneratedType.reused, sr.get().generatedType);
    }
    StepVerifier.create(DataGlobal.bridge.execute("use test; alter table player add column name varchar(20) not null")
        .flatMap(Result::getRowsUpdated)
        .next())
      .expectNext(0)
      .verifyComplete();
  }

  @Test
  void syncCreateSchemaSucceedWithExtraTable() {
    DataGlobal.bridge.execute("use test; create table if not exists something (id int not null)").blockLast();
    final Optional<SyncResult> sr = testDb.sync(DdlOption.create).blockOptional();
    if (sr.isPresent()) {
      if (sr.get().hasError()) {
        System.out.println(sr.get().errorMessage);
        fail();
      }
      assertEquals(GeneratedType.reused, sr.get().generatedType);
    }
    StepVerifier.create(DataGlobal.bridge.execute("use test; drop table if exists something")
        .flatMap(Result::getRowsUpdated)
        .next())
      .expectNext(0)
      .verifyComplete();
  }

  @Test
  void syncUpdateSchemaWithMissingTable() {
    StepVerifier.create(DataGlobal.bridge.execute("use test; drop table if exists player")
        .flatMap(Result::getRowsUpdated)
        .next())
      .expectNext(0)
      .verifyComplete();
    final Optional<SyncResult> sr = testDb.sync(DdlOption.update).blockOptional();
    if (sr.isPresent()) {
      if (sr.get().hasError()) {
        System.out.println(sr.get().errorMessage);
        fail();
      }
      assertEquals(GeneratedType.modified, sr.get().generatedType);
    }
    StepVerifier.create(DataGlobal.bridge.execute("use test; show tables")
        .flatMap(result -> result.map((row, rowMetadata) -> (String) row.get("tables_in_test")))
        .doOnNext(System.out::println)
        .filter(tableName -> tableName.equals("player")))
      .expectNext("player")
      .verifyComplete();
  }

  @Test
  void syncUpdateSchemaWithMissingColumn() {
    final DdlOption ddlOption = DdlOption.make().create().update();
    DdlOption.make((byte) 0b010);
    DataGlobal.bridge.execute("use test; alter table player drop column if exists name").blockLast();
    final Optional<SyncResult> sr = testDb.sync(ddlOption).blockOptional();
    if (sr.isPresent()) {
      System.out.println(sr.get());
      if (sr.get().hasError()) {
        System.out.println(sr.get().errorMessage);
        fail();
      }
      assertEquals(GeneratedType.modified, sr.get().generatedType);
    }
    StepVerifier.create(DataGlobal.bridge.execute("use test; show create table player")
        .flatMap(result -> result.map((row, rowMetadata) -> (String) row.get("create table")))
        .doOnNext(System.out::println)
        .filter(row -> row.contains("`name` varchar(20) NOT NULL,"))  //TODO: Make this work with just comparing testdb structure to the fromSql() object of the show create table result.
        .map(row -> row.replaceAll(".*", "").replaceAll("\n", "")))
      .expectNext("")
      .verifyComplete();
  }

  @Test
  void syncReplaceSchemaTest() {
    DataGlobal.bridge.execute("use test; alter table player modify column name int not null").blockLast();
    StepVerifier.create(testDb.sync(DdlOption.replace)
        .thenMany(DataGlobal.bridge.execute("use test; show create table player")
          .flatMap(result -> result.map((row, rowMetadata) -> (String) row.get("create table")))
          .doOnNext(System.out::println)
          .filter(e -> e.contains("`name` varchar(20) NOT NULL,"))
          .map(e -> true)
          .doOnNext(Assertions::assertTrue))
        .then(DataGlobal.bridge.execute("use test; select * from player")
          .flatMap(result -> result.map((row, rowMetadata) -> row.get("name")))
          .next()))
      .expectNextCount(0)
      .verifyComplete();
  }

  @Test
  void syncReplaceDropSchemaTest() {
    testDb.sync(DdlOption.replaceDrop);
    //TODO: Figure out how to use exit/shutdown signal to call a method which will drop the schema.
  }

*/
}
