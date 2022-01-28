package com.ple.jerbil.functional;

import com.ple.jerbil.data.DataGlobal;
import com.ple.jerbil.data.Database;
import com.ple.jerbil.data.DdlOption;
import com.ple.jerbil.data.GeneratedType;
import com.ple.jerbil.data.bridge.MariadbR2dbcBridge;
import com.ple.jerbil.testcommon.*;
import io.r2dbc.spi.Result;
import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;

import java.util.Optional;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

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

  @Test
  void syncCreateSchemaGeneratedSuccess() {
    StepVerifier.create(
        DataGlobal.bridge.execute("drop database if exists test")
          .flatMap(Result::getRowsUpdated)
          .map(numRowsUpdated -> true)
          .next())
      .expectNext(true)
      .verifyComplete();
    final Optional<Database> db = testDb.sync(DdlOption.create).blockOptional();
    if (db.isPresent()) {
      if (db.get().hasError()) {
        System.out.println(db.get().errorMessage);
        fail();
      }
    }
    assertEquals(GeneratedType.generated, db.get().generatedType);
  }

  @Test
  void syncCreateSchemaReusedSuccess() {
    final Optional<Database> db = testDb.sync(DdlOption.create).blockOptional();
    if (db.isPresent()) {
      if (db.get().hasError()) {
        System.out.println(db.get().errorMessage);
        fail();
      }
    }
    assertEquals(GeneratedType.reused, db.get().generatedType);
  }

  @Test
  void syncCreateSchemaFailWithMissingCol() {
    StepVerifier.create(
        DataGlobal.bridge.execute("use test; alter table player drop column if exists name")
          .flatMap(Result::getRowsUpdated)
          .next())
      .expectNext(0)
      .verifyComplete();
    StepVerifier.create(testDb.sync(DdlOption.create)
        .filter(Database::hasError))
      .consumeNextWith(db -> {
        System.out.println(db.errorMessage);
        assertEquals(GeneratedType.reused, db.generatedType);
      })
      .verifyComplete();
    StepVerifier.create(DataGlobal.bridge.execute("use test; alter table player add column name varchar(20) not null")
        .flatMap(Result::getRowsUpdated)
        .next())
      .expectNext(0)
      .verifyComplete();
  }

  @Test
  void syncCreateSchemaFailWithExtraTable() {
    StepVerifier.create(
        DataGlobal.bridge.execute("use test; create table if not exists something (id int not null)")
          .flatMap(Result::getRowsUpdated)
          .next())
      .expectNext(0)
      .verifyComplete();
    StepVerifier.create(testDb.sync(DdlOption.create)
        .filter(Database::hasError))
      .consumeNextWith(db -> {
        System.out.println(db.errorMessage);
        assertEquals(GeneratedType.reused, db.generatedType);
      })
      .verifyComplete();
    StepVerifier.create(DataGlobal.bridge.execute("use test; drop table something")
        .flatMap(Result::getRowsUpdated)
        .next())
      .expectNext(0)
      .verifyComplete();
  }

  @Test
  void syncUpdateSchemaWithMissingTable() {
    StepVerifier.create(DataGlobal.bridge.execute("use test; drop table player")
        .flatMap(Result::getRowsUpdated)
        .next())
      .expectNext(0)
      .verifyComplete();
    StepVerifier.create(testDb.sync(DdlOption.update)
        .filter(Database::hasError)
        .doOnNext(db -> System.out.println(db.errorMessage)))
      .verifyComplete();
    StepVerifier.create(DataGlobal.bridge.execute("use test; show tables")
        .flatMap(result -> result.map((row, rowMetadata) -> (String) row.get("tables_in_test")))
        .doOnNext(System.out::println)
        .filter(tableName -> tableName.equals("player")))
      .expectNext("player")
      .verifyComplete();
  }

  @Test
  void syncUpdateSchemaWithMissingColumn() {
    StepVerifier.create(
        DataGlobal.bridge.execute("use test; alter table player drop column if exists name")
          .flatMap(Result::getRowsUpdated)
          .next())
      .expectNext(0)
      .verifyComplete();
    StepVerifier.create(testDb.sync(DdlOption.update)
        .filter(Database::hasError)
        .doOnNext(db -> System.out.println(db.errorMessage)))
      .verifyComplete();
    StepVerifier.create(DataGlobal.bridge.execute("use test; show create table player")
        .flatMap(result -> result.map((row, rowMetadata) -> (String) row.get("create table")))
        .doOnNext(System.out::println)
        .filter(row -> row.contains("`name` varchar(20) NOT NULL,"))  //TODO: Make this work with just comparing testdb structure to the fromSql() object of the show create table result.
        .map(row -> row.replaceAll(".*", "").replaceAll("\n", ""))
      )
      .expectNext("")
      .verifyComplete();
  }

/*
  @Test
  void syncReplaceSchemaTest() {
    DataGlobal.bridge.execute("use test; alter table player modify column name int not null").subscribe();
    testDb.sync(DdlOption.replace);
    StepVerifier.create(DataGlobal.bridge.execute("use test; show create table player")
        .flatMap(result -> result.map((row, rowMetadata) -> (String) row.get("`create table`")))
        .filter(e -> e.contains("`name` varchar(20) NOT NULL,"))
        .map(e -> true))
      .expectNext(true)
      .verifyComplete();
    StepVerifier.create(DataGlobal.bridge.execute("select * from player")
      .flatMap(result -> result.map((row, rowMetadata) -> row.get("name"))))
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
