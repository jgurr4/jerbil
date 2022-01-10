package com.ple.jerbil;

import com.ple.jerbil.data.DataGlobal;
import com.ple.jerbil.data.bridge.MariadbR2dbcBridge;
import com.ple.jerbil.testcommon.ConfigProps;
import io.r2dbc.spi.Result;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;
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
    final Flux<Result> result = DataGlobal.bridge.execute("drop database test");
    result.subscribe();
  }

  @Test
  @Order(2)
  void createDbTest() {
    final Flux<Result> result = DataGlobal.bridge.execute("create database test");
    StepVerifier.create(result.log()).expectComplete();
    result.subscribe();
  }

  @Test
  @Order(3)
  void createMultipleTablesTest() {
    final Flux<Result> result = DataGlobal.bridge.execute("""
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
    final Flux<Result> result = DataGlobal.bridge.execute("use test; insert into item values (0, 'fire sword', 'weapon', 200), (0, 'robe', 'armor', 300)");
    result.map(Result::getRowsUpdated)
      .doOnNext(System.out::println)
      .subscribe();
  }

  @Test
  @Order(5)
  void selectQueryTest() {
    final Flux<Result> result = DataGlobal.bridge.execute("use test; select itemId, name, type, price from item;");
    result
      .publishOn(Schedulers.single()).flatMap(results -> results.map((row, rowMetadata) -> String.format("%d | %s | %s | %d",
        row.get("itemId", Long.class),
        row.get("name", String.class),
        row.get("type", String.class),
        row.get("price", Integer.class))))
      .doOnNext(System.out::println)
      .subscribe();

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

}
