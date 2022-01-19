package com.ple.jerbil.functional;

import com.ple.jerbil.data.DataGlobal;
import com.ple.jerbil.data.Database;
import com.ple.jerbil.data.DdlOption;
import com.ple.jerbil.data.SchemaType;
import com.ple.jerbil.data.bridge.MariadbR2dbcBridge;
import com.ple.jerbil.testcommon.*;
import org.junit.jupiter.api.Test;

import java.util.Objects;
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
  void syncCreateSchemaSuccess() {
    assertFalse(Objects.requireNonNull(testDb.sync(DdlOption.create).block()).hasError());
    assertEquals(SchemaType.reused, Objects.requireNonNull(testDb.sync(DdlOption.create).block()).schemaType);
  }

  //TODO: Make a note. This is actually ok to not exit program. Instead just log the error and don't perform any more queries
  // when the object is returned the user will be able to see the error log if they choose to. If they want, they can make it
  // exit the program. It's actually not good practice for libraries to exit the app. What if you're doing work that needs
  // to happen on a system which it cannot exit on? But this functor method is nice because it actually won't exit, but still
  // it will prevent further processes from executing after the first error. And the user can log the details in the functor from there.
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
  @Order(7)
  void syncUpdateSchemaTest() {
    DataGlobal.bridge.execute("use test; alter table player modify column name int not null").subscribe();
    testDb.sync(DdlOption.update);
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
