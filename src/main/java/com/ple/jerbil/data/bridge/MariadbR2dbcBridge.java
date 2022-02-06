package com.ple.jerbil.data.bridge;

import com.ple.jerbil.data.*;
import com.ple.jerbil.data.query.Table;
import com.ple.jerbil.data.translator.MysqlLanguageGenerator;
import com.ple.util.IArrayList;
import io.r2dbc.pool.ConnectionPool;
import io.r2dbc.pool.ConnectionPoolConfiguration;
import io.r2dbc.spi.Result;
import org.jetbrains.annotations.Nullable;
import org.mariadb.r2dbc.MariadbConnectionConfiguration;
import org.mariadb.r2dbc.MariadbConnectionFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Immutable
public class MariadbR2dbcBridge implements DataBridge {

  public final LanguageGenerator generator = MysqlLanguageGenerator.make();
  public final String driver;
  public final String host;
  public final int port;
  public final String username;
  public final String password;
  @Nullable
  public final String database;
  public final ConnectionPool pool;

  protected MariadbR2dbcBridge(String driver, String host, int port, String username, String password, @Nullable String database, ConnectionPool pool) {
    this.driver = driver;
    this.host = host;
    this.port = port;
    this.username = username;
    this.password = password;
    this.database = database;
    this.pool = pool;
  }

  public static MariadbR2dbcBridge make(String driver, String host, int port, String username, String password, String database, ConnectionPool pool) {
    return new MariadbR2dbcBridge(driver, host, port, username, password, database, pool);
  }

  public static DataBridge make(String driver, String host, int port, String username, String password, String database) {
    return new MariadbR2dbcBridge(driver, host, port, username, password, database, startConnection(host, port, username, password, database));
  }

  public static DataBridge make(String driver, String host, int port, String username, String password) {
    return new MariadbR2dbcBridge(driver, host, port, username, password, null, startConnection(host, port, username, password, null));
  }

  public static DataBridge make(String host, int port, String username, String password) {
    return new MariadbR2dbcBridge("r2dbc:mariadb", host, port, username, password, null, startConnection(host, port, username, password, null));
  }

  @Override
  public LanguageGenerator getGenerator() {
    return generator;
  }

  public Mono<MariadbR2dbcBridge> getConnectionPool() {
    if (pool == null) {
      return createConnectionPool();
    }
    if (pool.getMetrics().isPresent()) {
      if (pool.getMetrics().get().acquiredSize() == pool.getMetrics().get().getMaxAllocatedSize()) {
        return createConnectionPool();
      }
    }
    return Mono.just(this);
  }

  @Override
  public Flux<Result> execute(String sql) {
    return this.getConnectionPool()
      .map(bridge -> bridge.pool)
      .flatMap(pool -> pool.create())
      .map(conn -> conn.createStatement(sql))
      .flatMapMany(statement -> statement.execute());
  }

  @Override
  public Result executeSynchronously(String sql) {
    return execute(sql).blockLast();
  }

  public Database getDb(String name) {
    return DataGlobal.bridge.execute("show databases")
      .flatMap(result -> result.map((row, rowMetadata) -> (String) row.get(0)))
      .filter(db -> db.equals(name))
      .next()
      .flatMapMany(db -> DataGlobal.bridge.execute("use " + name + "; show tables")
        .flatMap(result -> result.map((row, rowMetadata) -> (String) row.get(0))))
        .flatMap(tblName -> DataGlobal.bridge.execute("use " + name + "; show create table " + tblName)
          .flatMap(result -> result.map((row, rowMetadata) -> (String) row.get(1))))
          .map(tblCreateStr -> DataGlobal.bridge.getGenerator().fromSql(tblCreateStr))
          .collectList()
          .log()
          //FIXME: Currently this only outputs the first create table string.
          .map(tables -> IArrayList.make(tables.toArray(new Table[0])))
          .map(tablesIList -> Database.make(name, tablesIList)).block();
  }
//          .collect(IArrayList::make, (s, table) -> tables.add(table))  //Alternative method.
//            .map(iListTables -> Database.make(name, iListTables))

/*
public Database getDb(String name) {
  return DataGlobal.bridge.execute("show databases")
    .flatMap(result -> result.map((row, rowMetadata) -> (String) row.get(0)))
    .filter(db -> db.equals(name))
    .next()
    .flatMap(db -> DataGlobal.bridge.execute("use " + name + "; show tables")
      .flatMap(result -> result.map((row, rowMetadata) -> (String) row.get(0)))
      //FIXME: Currently this only outputs the first tablename.
      .flatMap(tblName -> DataGlobal.bridge.execute("use " + name + "; show create table " + tblName)
        .flatMap(result -> result.map((row, rowMetadata) -> (String) row.get(1)))
        .map(tblCreateStr -> DataGlobal.bridge.getGenerator().fromSql(tblCreateStr))
        .collectList()
        .map(tables -> IArrayList.make(tables.toArray(new Table[0])))
        .map(tablesIList -> Database.make(name, tablesIList)))
      .next()).block();
}
*/

  private Mono<MariadbR2dbcBridge> createConnectionPool() {
    return Mono.just(startConnection(host, port, username, password, database))
      .map(pool -> MariadbR2dbcBridge.make(driver, host, port, username, password, database, pool))
      .doOnError(e -> {
        throw new IllegalArgumentException("Issue creating connection pool");
      })
      .doOnSuccess(bridge -> {
        if (bridge.pool != null) bridge.pool.close();
      });
  }

  private static ConnectionPool startConnection(String host, int port, String username, String password, String database) {
    final MariadbConnectionConfiguration factoryConfig = MariadbConnectionConfiguration.builder()
      .host(host)
      .port(port)
      .username(username)
      .allowMultiQueries(true)
      .password(password)
      .database(database)
      .build();
    //TODO: Add SSl configuration options above.
    final MariadbConnectionFactory connFactory = new MariadbConnectionFactory(factoryConfig);
    final ConnectionPoolConfiguration poolConfig = ConnectionPoolConfiguration
      .builder(connFactory)
      .maxIdleTime(Duration.ofMillis(1000))
      .maxSize(20)
      .build();
    return new ConnectionPool(poolConfig);
  }

}
