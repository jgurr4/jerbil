package com.ple.jerbil;

import com.ple.jerbil.data.DataGlobal;
import com.ple.jerbil.data.bridge.MariadbR2dbcBridge;
import com.ple.jerbil.testcommon.ConfigProps;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mariadb.r2dbc.api.MariadbResult;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.Properties;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BridgeTests {

  public BridgeTests() {
    final Properties props = ConfigProps.getProperties();
    DataGlobal.bridge = MariadbR2dbcBridge.make(props.getProperty("driver"), props.getProperty("host"), Integer.parseInt(props.getProperty("port")), props.getProperty("user"), props.getProperty("password"));
  }

  @Test
  @Order(1)
  void dropDbTest() {
    final Properties props = ConfigProps.getProperties();
    DataGlobal.bridge = MariadbR2dbcBridge.make(props.getProperty("driver"), props.getProperty("host"), Integer.parseInt(props.getProperty("port")), props.getProperty("user"), props.getProperty("password"));
    final Flux<MariadbResult> result = DataGlobal.bridge.execute("drop database test");
    result.subscribe();
  }

  //TODO: Make it so the tests will drop the database and recreate it every time.
  @Test
  @Order(2)
  void createDbTest() {
    final Flux<MariadbResult> result = DataGlobal.bridge.execute("create database test");
    result.subscribe();
    StepVerifier.create(result.log()).expectComplete();
  }

  @Test
  @Order(3)
  void createMultipleTablesTest() {
    final Flux<MariadbResult> result = DataGlobal.bridge.execute("""
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
      """);
    result.doOnNext(mariadbResult -> System.out.println("Statement successful!")).subscribe();
  }

  //FIXME: Currently this says "MonoSingle" instead of "1 row updated".
  @Test
  @Order(4)
  void insertRowTest() {
    final Flux<MariadbResult> result = DataGlobal.bridge.execute("use test; insert into item values (0, 'fire sword', 'weapon', 200), (0, 'robe', 'armor', 300)");
    result.map(MariadbResult::getRowsUpdated)
      .doOnNext(System.out::println)
      .subscribe();
  }

  @Test
  @Order(5)
  void selectQueryTest() {
    final Flux<MariadbResult> result = DataGlobal.bridge.execute("use test; select itemId, name, type, price from item;");
    result.flatMap(mariadbResult -> mariadbResult.map((row, rowMetadata) -> String.format( "%d | %s | %s | %d",
      row.get("itemId", Long.class),
      row.get("name", String.class),
      row.get("type", String.class),
      row.get("price", Integer.class))))
      .doOnNext(System.out::println)
      .subscribe();

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

}
