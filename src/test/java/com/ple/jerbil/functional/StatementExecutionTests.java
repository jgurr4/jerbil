package com.ple.jerbil.functional;

import com.ple.jerbil.data.DataGlobal;
import com.ple.jerbil.data.Database;
import com.ple.jerbil.data.DatabaseBuilder;
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

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class StatementExecutionTests {

  final TestDatabaseContainer testDb = DatabaseBuilder.generate(TestDatabaseContainer.class, "test");
  final UserTableContainer user = testDb.user;
  final ItemTableContainer item = testDb.item;
  final PlayerTableContainer player = testDb.player;
  final InventoryTableContainer inventory = testDb.inventory;

  public StatementExecutionTests() {
    final Properties props = ConfigProps.getProperties();
    DataGlobal.bridge = MariadbR2dbcBridge.make(props.getProperty("driver"), props.getProperty("host"), Integer.parseInt(props.getProperty("port")), props.getProperty("user"), props.getProperty("password"));
  }

  /*
  @Test
  @Order(1)
  void dropDbTest() {
    final Flux<Result> result = DataGlobal.bridge.execute("drop database test").unwrapFlux(); //FIXME: Remove all hard-coded sql statements in this file. Replace with Jerbil commands.
    StepVerifier.create(result
        .doOnSubscribe(n -> System.out.println("Successfully dropped database")))
      .expectNextCount(1)
      .verifyComplete();
  }

  @Test
  @Order(2)
  void createDbTest() {
    final Flux<Result> result = DataGlobal.bridge.execute("create database test").unwrapFlux();
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
      """).unwrapFlux().blockLast(Duration.ofMillis(100));
    final Flux<Result> result = DataGlobal.bridge.execute("use test; show tables;").unwrapFlux();
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
    final Flux<Result> result = DataGlobal.bridge.execute("use test; insert into item values (0, 'fire sword', 'weapon', 200), (0, 'robe', 'armor', 300)").unwrapFlux();
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
    final Flux<Result> result = DataGlobal.bridge.execute("use test; select itemId, name, type, price from item;").unwrapFlux();
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
  }
   */
}
