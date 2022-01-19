package com.ple.jerbil.functional;

import com.ple.jerbil.data.DataGlobal;
import com.ple.jerbil.data.Database;
import com.ple.jerbil.data.DdlOption;
import com.ple.jerbil.data.SchemaType;
import com.ple.jerbil.data.bridge.MariadbR2dbcBridge;
import com.ple.jerbil.testcommon.*;
import io.r2dbc.spi.Result;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.time.Duration;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
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
  @Order(1)
  void dropDbTest() {
    DataGlobal.bridge.execute("set global max_connections=300").subscribe();  //151 is default. Not required for testing, but can be useful later in configuring server max connections.
    final Flux<Result> result = DataGlobal.bridge.execute("drop database test");
    StepVerifier.create(result
        .doOnSubscribe(n -> System.out.println("Successfully dropped database")))
      .expectNextCount(1)
      .verifyComplete();
  }

  @Test
  @Order(2)
  void createDbTest() {
    final Flux<Result> result = DataGlobal.bridge.execute("create database test");
    StepVerifier.create(
        result
          .flatMap(Result::getRowsUpdated)
          .doOnNext(n -> System.out.println("Successfully created " + n + " database.")))
      .expectNext(1)
      .verifyComplete();
  }

  @Test
  @Order(3)
  void createMultipleTablesTest() {
    DataGlobal.bridge.execute("""
        use test;
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
      """).blockLast(Duration.ofMillis(100));
    final Flux<Result> result = DataGlobal.bridge.execute("use test; show tables;");
    StepVerifier.create(result
        .flatMap(results -> results.map((row, rowMetadata) -> String.format("%s",
          row.get("tables_in_test", String.class))))
        .doOnNext(s -> System.out.println("Successfully created table: " + s)))
      .expectNext("inventory", "item", "player", "user")
      .verifyComplete();
  }

  @Test
  @Order(4)
  void insertRowTest() {
    final Flux<Result> result = DataGlobal.bridge.execute("use test; insert into item values (0, 'fire sword', 'weapon', 200), (0, 'robe', 'armor', 300)");
    StepVerifier.create(
        result
          .flatMap(Result::getRowsUpdated)
          .filter(n -> n > 0)
          .doOnNext(n -> System.out.println("Successfully inserted " + n + " rows")))
      .expectNext(2)
      .verifyComplete();
  }

  @Test
  @Order(5)
  void selectQueryTest() {
    final Flux<Result> result = DataGlobal.bridge.execute("use test; select itemId, name, type, price from item;");
    StepVerifier.create(result
        .flatMap(results -> results.map((row, rowMetadata) -> String.format("%d | %s | %s | %d",
          row.get("itemId", Long.class),
          row.get("name", String.class),
          row.get("type", String.class),
          row.get("price", Integer.class))))
        .doOnNext(System.out::println)
        .map(s -> s.substring(s.length() - 3)))
      .expectNext("200", "300")
      .verifyComplete();

/*
    // This was the old way to subscribe to an object. But now instead of using subscribe with arguments, we use .methods()
    // and operators on the publisher and use subscribe at the end. See https://www.woolha.com/tutorials/project-reactor-using-subscribe-on-mono-and-flux
    result.subscribe(
      result1 -> System.out.println(result1),
      error -> System.err.println("Error: " + error),
      () -> System.out.println("Done"),   //
      subscription -> subscription.request(2)   //pass on two elements from the source to a new consumer that will be invoked on subscribe signal.
    )
*/

/* Alternative method:
    for (String item_entry : result.flatMap( res ->
      res.map((row, metadata) -> String.format( "%d | %s | %s | %d",
      row.get("itemId", Long.class),
      row.get("name", String.class),
      row.get("type", String.class),
      row.get("price", Integer.class)))).toIterable()) {
      System.out.println(item_entry);
    }
*/
  }

  //FIXME: The sync method should not return a Database functor. It should return a Promise/future of Database functor like Flux<Database> or Mono<Database>. Then the user can decide if they want to do things
  // asynchronously or synchronously. Anything related to the mysql bridge should be asynchronous.
  @Test
  @Order(6)
  void syncCreateSchemaSuccess() {
    DataGlobal.bridge.execute("drop database test").subscribe();
    assertFalse(testDb.sync(DdlOption.create).hasError());
    assertEquals(SchemaType.reused, testDb.sync(DdlOption.create).schemaType);
  }

  //TODO: Make a note. This is actually ok to not exit program. Instead just log the error and don't perform any more queries
  // when the object is returned the user will be able to see the error log if they choose to. If they want, they can make it
  // exit the program. It's actually not good practice for libraries to exit the app. What if you're doing work that needs
  // to happen on a system which it cannot exit on? But this functor method is nice because it actually won't exit, but still
  // it will prevent further processes from executing after the first error. And the user can log the details in the functor from there.
  @Test
  @Order(7)
  void syncCreateSchemaFail() {
    DataGlobal.bridge.execute("use test; create table if not exists something (id int not null); drop table inventory").subscribe();
    final Database syncAfterChange = testDb.sync(DdlOption.create);
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
