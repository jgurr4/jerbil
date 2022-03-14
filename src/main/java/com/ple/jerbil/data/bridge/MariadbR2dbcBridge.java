package com.ple.jerbil.data.bridge;

import com.ple.jerbil.data.*;
import com.ple.jerbil.data.query.TableContainer;
import com.ple.jerbil.data.reactiveUtils.ReactiveFlux;
import com.ple.jerbil.data.reactiveUtils.ReactiveMono;
import com.ple.jerbil.data.translator.LanguageGenerator;
import com.ple.jerbil.data.translator.MariadbLanguageGenerator;
import com.ple.util.*;
import io.r2dbc.pool.ConnectionPool;
import io.r2dbc.pool.ConnectionPoolConfiguration;
import io.r2dbc.spi.ReadableMetadata;
import io.r2dbc.spi.Result;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.mariadb.r2dbc.MariadbConnectionConfiguration;
import org.mariadb.r2dbc.MariadbConnectionFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@Immutable
public class MariadbR2dbcBridge implements DataBridge {

  public final LanguageGenerator generator = MariadbLanguageGenerator.make();
  public final String host;
  public final int port;
  public final String username;
  public final String password;
  @Nullable public final String database;
  public final ConnectionPool pool;

  protected MariadbR2dbcBridge(String host, int port, String username, String password, @Nullable String database,
                               ConnectionPool pool) {
    this.host = host;
    this.port = port;
    this.username = username;
    this.password = password;
    this.database = database;
    this.pool = pool;
  }

  public static MariadbR2dbcBridge make(String host, int port, String username, String password,
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
        .maxSize(10)
        .build();
    return new MariadbR2dbcBridge(host, port, username, password, database, new ConnectionPool(poolConfig));
  }

  private Mono<MariadbR2dbcBridge> makeReactive(String host, int port, String username, String password,
                                                String database) {
    return Mono.just(make(host, port, username, password, database))
        .doOnError(e -> {
          throw new IllegalArgumentException("Issue creating connection pool");
        });
  }

  public static DataBridge make(String host, int port, String username, String password) {
    return make(host, port, username, password, null);
  }

  @Override
  public LanguageGenerator getGenerator() {
    return generator;
  }

  //FIXME: This method
  @Override
  public ReactiveFlux<DbResult> execute(String sql) {
    return ReactiveFlux.make(Mono.just(this.pool)
        .flatMap(pool -> {
          return pool.create();
        })
        .map(conn -> {
          return conn.createStatement(sql);
        })
        .flatMapMany(statement -> {
          return statement.execute();
        })
        .flatMap(result -> {
          final ReactiveMono<DbResult> dbResult = getDbResult(result);
          return dbResult.unwrapMono();
        }));
  }

  public static ReactiveMono<DbResult> getDbResult(Result result) {
    IList<String> warningList = IArrayList.empty;
    IList<String> errorList = IArrayList.empty;
    List<String> colNames = new ArrayList<>();
    List<Object> values = new ArrayList<>();
    return ReactiveMono.make(
        Flux.from(
                result.map(
                    (row, rowMetadata) -> {
                      final List<String> names = rowMetadata.getColumnMetadatas().stream()
                          .map(ReadableMetadata::getName).toList();
                      if (colNames.size() == 0) {
                        colNames.addAll(names);
                      }
                      for (int i = 0; i < colNames.size(); i++) {
                        values.add(row.get(i));
                      }
                      return row;
                    }))
            .next()
            .flatMap(row -> Mono.from(result.getRowsUpdated()))
            .map(ru -> {
              final ITable iTableResult = ITable.make(IArrayList.make(colNames), values.toArray());
              return DbResult.make(iTableResult, errorList, warningList, ru);
            })
            .defaultIfEmpty(DbResult.empty));
  }

  public ReactiveMono<DatabaseContainer> getDb(String name) {
    Database database = Database.make(name);
    return ReactiveMono.make(execute("show create database " + name).unwrapFlux()
        .flatMap(result ->
            Flux.just((String[]) result.getColumn("create database"))
                .next()
                .flatMap(dbCreateStr -> execute("use " + name + "; show tables")
                    .flatMap(result1 -> Flux.just((String[]) result.getColumn("tables_in_" + name)))
                    .unwrapFlux().collectList()
                    .map(tableNameList -> {
                      final IList<String> tableNameIList = IArrayList.make(tableNameList.toArray(new String[0]));
                      return convertToDbContainer(tableNameIList, name, database);
                    }))).next()).next();
  }

  @Override
  public <T extends DbRecord, I extends DbRecordId> I save(T record) {
    return null;
  }

  @NotNull
  private ReactiveMono<DatabaseContainer> convertToDbContainer(IList<String> tblNameList, String dbName,
                                                               Database database) {
    return ReactiveMono.make(Flux.fromIterable(tblNameList)
        .flatMap(tblName -> execute("use " + dbName + "; show create table " + tblName)
            .flatMap(dbResult -> Flux.just((String[]) dbResult.getColumn("create table")))
            .map(tblCreateStr -> generator.getTableFromSql(tblCreateStr, database))
            .unwrapFlux()
            .collectList()
            .map(tableContainers -> (IList<TableContainer>) (IList) IArrayList.make(tableContainers.toArray()))
            .map(tables -> convertListToIMap(tables))
            .map(tablesIList -> DatabaseContainer.make(database, tablesIList))).next());
  }

  private IMap<String, TableContainer> convertListToIMap(IList<TableContainer> tables) {
    IMap<String, TableContainer> map = IArrayMap.empty;
    for (TableContainer table : tables) {
      map = map.put(table.table.tableName, table);
    }
    return map;
  }
}
