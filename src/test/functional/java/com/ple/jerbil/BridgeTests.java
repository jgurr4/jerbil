package com.ple.jerbil;

import com.ple.jerbil.data.DataGlobal;
import com.ple.jerbil.data.bridge.MariadbR2dbcBridge;
import com.ple.jerbil.testcommon.ConfigProps;
import org.junit.jupiter.api.Test;
import org.mariadb.r2dbc.api.MariadbResult;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class BridgeTests {


  public BridgeTests() {
    final Properties props = ConfigProps.getProperties();
    DataGlobal.bridge = MariadbR2dbcBridge.make(props.getProperty("driver"), props.getProperty("host"), Integer.parseInt(props.getProperty("port")), props.getProperty("user"), props.getProperty("password"));
  }

  @Test
  void createDbTest() {
    final Mono<MariadbResult> result = DataGlobal.bridge.execute("create database test");
    result.doOnSuccess(bool -> assertTrue(true)).subscribe();
  }

  @Test
  void createMultipleTablesTest() {
    final Mono<MariadbResult> result = DataGlobal.bridge.execute("""
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
    result.doOnSuccess(bool -> assertTrue(true)).subscribe();
  }

  @Test
  void selectQueryTest() {
    final Flux<MariadbResult> result = DataGlobal.bridge.executeQuery("select itemId, name, type, price from item");
    List<String> results = List.of();
    for (MariadbResult mariadbResult : result.toIterable()) {
      results = mariadbResult.map((row, metadata) -> String.format("- %s %s <%s>",
          // Get itemId
          row.get(0, Long.class),

          //  Get name
          row.get(1, String.class),

          //  Get type
          row.get(2, String.class),

          //Get price
          row.get(3, Integer.class))
        //TODO: See if this needs to be changed to .subscribe or some other Reactive Streams method. Since this looks like it is turning back to normal streams. Which might break things possibly.
        ).toStream()
        .peek(e -> System.out.println(e))
        .collect(Collectors.toList());
    }
    System.out.println(results);
  }

  @Test
  void insertRowTest() {
    final Flux<MariadbResult> result = DataGlobal.bridge.executeQuery("insert into item values (0, 'ice sword', 'weapon', 200)");
    //TODO: Find a way to do this with different result.method(), like result.map().subscribe().
    for (MariadbResult mariadbResult : result.toIterable()) {
      System.out.println(mariadbResult.getRowsUpdated());
    }
    //TODO: Decide if I need to remove the executeUpdate method since it seems R2dbc works fine with just using executeQuery for everything including updates.
//    final Mono<Long> result = dataBridge.executeUpdate("insert into item values (0, 'ice sword', 'weapon', 200)");

  }

}
