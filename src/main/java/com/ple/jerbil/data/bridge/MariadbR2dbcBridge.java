package com.ple.jerbil.data.bridge;

import com.ple.jerbil.data.*;
import com.ple.jerbil.data.GenericInterfaces.*;
import com.ple.jerbil.data.query.TableContainer;
import com.ple.jerbil.data.selectExpression.Column;
import com.ple.jerbil.data.translator.LanguageGenerator;
import com.ple.jerbil.data.translator.MariadbLanguageGenerator;
import com.ple.util.IArrayMap;
import com.ple.util.IEntry;
import com.ple.util.IMap;
import io.r2dbc.pool.ConnectionPool;
import io.r2dbc.pool.ConnectionPoolConfiguration;
import io.r2dbc.spi.Result;
import org.jetbrains.annotations.Nullable;
import org.mariadb.r2dbc.MariadbConnectionConfiguration;
import org.mariadb.r2dbc.MariadbConnectionFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;

@Immutable
public class MariadbR2dbcBridge implements DataBridge {

  public final LanguageGenerator generator = MariadbLanguageGenerator.make();
  public final String driver;
  public final String host;
  public final int port;
  public final String username;
  public final String password;
  @Nullable public final String database;
  public final ConnectionPool pool;

  protected MariadbR2dbcBridge(String driver, String host, int port, String username, String password,
                               @Nullable String database, ConnectionPool pool) {
    this.driver = driver;
    this.host = host;
    this.port = port;
    this.username = username;
    this.password = password;
    this.database = database;
    this.pool = pool;
  }

  public static MariadbR2dbcBridge make(String driver, String host, int port, String username, String password,
                                        String database, ConnectionPool pool) {
    return new MariadbR2dbcBridge(driver, host, port, username, password, database, pool);
  }

  public static DataBridge make(String driver, String host, int port, String username, String password,
                                String database) {
    return new MariadbR2dbcBridge(
        driver, host, port, username, password, database, startConnection(host, port, username, password, database));
  }

  public static DataBridge make(String driver, String host, int port, String username, String password) {
    return new MariadbR2dbcBridge(
        driver, host, port, username, password, null, startConnection(host, port, username, password, null));
  }

  public static DataBridge make(String host, int port, String username, String password) {
    return new MariadbR2dbcBridge(
        "r2dbc:mariadb", host, port, username, password, null, startConnection(host, port, username, password, null));
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
  public <T extends Result> ReactiveWrapper<Failable<T>> execute(String sql) {
    return ReactorFlux.make(this.getConnectionPool()
        .map(bridge -> bridge.pool)
        .flatMap(pool -> pool.create())
        .map(conn -> conn.createStatement(sql))
        .flatMapMany(statement -> (Flux<T>) statement.execute())
        .map(result1 -> Failable.make(result1, null, null))
        .onErrorContinue(
            (err, result) -> Failable.make(null, "Something went wrong with bridge.execute", err))
    );
  }

  //FIXME: This does not return the Failable in case the object is null. which means I cannot get the original exception
  // and error message from execute().
  public Flux<Result> executeAndUnwrap(String sql) {
    return execute(sql).unwrapFlux().map(result -> result.object).filter(obj -> obj != null);
  }

  @Override
  public ReactiveWrapper<Result> execute(ReactorMono<String> sql) {
    return ReactorFlux.make(
        sql.unwrapMono()
            .flatMapMany(
                sqlString -> executeAndUnwrap(sqlString)
            )
    );
  }

  public ReactiveWrapper<Failable<DatabaseContainer>> getDb(String name) {
    Database database = Database.make(name);
    return ReactorMono.make(executeAndUnwrap("show databases")
        .flatMap(result -> result.map((row, rowMetadata) -> (String) row.get(0)))
        .filter(db -> db.equals(name))
        .next()
        .map(db -> executeAndUnwrap("show create database " + db))
        //Here is where you put more methods to retrieve database level properties to put into database, like obtaining charset and
        .flatMapMany(
            db -> db
                .flatMap(result -> result.map((row, rowMetadata) -> (String) row.get("create database"))))
        .next()
        .map(dbCreateString -> getGenerator().fromSql(dbCreateString))
        .flatMapMany(db -> executeAndUnwrap("use " + name + "; show tables")
                .flatMap(result -> result.map((row, rowMetadata) -> (String) row.get(0)))
                .flatMap(tblName -> executeAndUnwrap("use " + name + "; show create table " + tblName)
                    .flatMap(result -> result.map((row, rowMetadata) -> (String) row.get(1))))
                .map(tblCreateStr -> {
                  final TableContainer table = getGenerator().fromSql(tblCreateStr, db);
                  return getGenerator().fromSql(tblCreateStr, table);
                })
                .collectList()
                .map(columns -> TableContainer.make(columns.get(0).table,
                    IArrayMap.make(columns.toArray(Column.emptyArray))))
        )
            .collectList()
            .map(tables -> convertListToIMap(tables))
            .map(tables -> DatabaseContainer.make(database, tables))
        //FIXME: After getting executeAndUnwrap fixed, replace this code to match those changes.
        .map(list -> Failable.make(list, null, null))
        .defaultIfEmpty(Failable.make(null, "failed", new RuntimeException("failed for some reason.")))
    );
  }

  private IMap<String, TableContainer> convertListToIMap(List<TableContainer> tables) {
    IMap<String, TableContainer> map = IArrayMap.empty;
    for (TableContainer table : tables) {
      map = map.put(table.table.tableName, table);
    }
    return map;
  }

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

  private static ConnectionPool startConnection(String host, int port, String username, String password,
                                                String database) {
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
