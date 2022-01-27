package com.ple.jerbil.functional;

import com.ple.jerbil.data.DataGlobal;
import com.ple.jerbil.data.Database;
import com.ple.jerbil.data.DdlOption;
import com.ple.jerbil.data.SchemaType;
import com.ple.jerbil.data.bridge.MariadbR2dbcBridge;
import com.ple.jerbil.testcommon.*;
import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;

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
    DataGlobal.bridge = MariadbR2dbcBridge.make(props.getProperty("driver"), props.getProperty("host"), Integer.parseInt(props.getProperty("port")), props.getProperty("user"), props.getProperty("password"));
  }

  @Test
  void syncCreateSchemaGeneratedSuccess() {
    final Database db = testDb.sync(DdlOption.create).block();
    if (db.hasError()) {
      System.out.println(db.errorMessage);
      fail();
    }
    assertEquals(SchemaType.generated, db.schemaType);
  }

  @Test
  void syncCreateSchemaReusedSuccess() {
    final Database db = testDb.sync(DdlOption.create).block();
    if (db.hasError()) {
      System.out.println(db.errorMessage);
      fail();
    }
    assertEquals(SchemaType.reused, db.schemaType);
  }

  @Test
  void syncCreateSchemaReusedFail() {
//    StepVerifier.create(
//        DataGlobal.bridge.execute("use test; alter table player drop column name")
//          .flatMap(result -> result.getRowsUpdated())
//          .next())
//      .expectNext(0)
//      .verifyComplete();
    StepVerifier.create(testDb.sync(DdlOption.create)
        .filter(db -> db.hasError()))
      .consumeNextWith(db -> {
        System.out.println(db.errorMessage);
        assertEquals(SchemaType.reused, db.schemaType);
      })
      .verifyComplete();
    StepVerifier.create(DataGlobal.bridge.execute("use test; alter table player add column name varchar(20) not null")
        .flatMap(result -> result.getRowsUpdated())
        .next())
      .expectNext(0)
      .verifyComplete();
  }

  @Test
  void syncCreateSchemaFail() {
    DataGlobal.bridge.execute("use test; create table if not exists something (id int not null); drop table inventory").subscribe();
    final Database syncAfterChange = testDb.sync(DdlOption.create).block();
    assert syncAfterChange != null;
    assertTrue(syncAfterChange.hasError());
    System.out.println(syncAfterChange.errorMessage);
    DataGlobal.bridge.execute("use test; drop table something; create table inventory (playerId int, itemId int, primary key (playerId, itemId))ENGINE=Aria;").subscribe();
  }

/*
  @Test
  void syncUpdateSchemaTest() {
    DataGlobal.bridge.execute("use test; alter table player modify column name int not null").subscribe();
    assertFalse(testDb.sync(DdlOption.update).block().hasError());
    StepVerifier.create(DataGlobal.bridge.execute("use test; show create table player")
      .flatMap(result -> result.map((row, rowMetadata) -> (String) row.get("create table")))
      .filter(e -> e.contains("`name` varchar(20) NOT NULL,"))
        .map(e -> true))
      .expectNext(true)
      .verifyComplete();
  }

  @Test
  @Order(8)
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
  @Order(9)
  void syncReplaceDropSchemaTest() {
    testDb.sync(DdlOption.replaceDrop);
    //TODO: Figure out how to use exit/shutdown signal to call a method which will drop the schema.
  }
*/

}
