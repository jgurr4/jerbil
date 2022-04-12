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
import io.r2dbc.spi.Row;
import org.jetbrains.annotations.NotNull;
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

  private Mono<MariadbR2dbcBridge> reactiveMake(String host, int port, String username, String password,
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
          return dbResult.unwrapMono();  //FIXME: Add flatMap support for ReactiveMono without needing to use unwrapMono().
//          return Mono.just(DbResult.empty);
        }).filter(
            dbResult -> dbResult.result.columnNames.size() > 0)); //TODO: Remove this filtering when we get segments supported.
  }

  @Immutable
  private static class rowColData {
    @Nullable public final IList<Object> rowValues;
    @Nullable public final IList<String> colNames;

    public rowColData(IList<Object> rowValues, IList<String> colNames) {
      this.rowValues = rowValues;
      this.colNames = colNames;
    }
  }

  /**
   * Segments are a feature of R2DBC that is not yet implemented inside of MariaDBR2DBC/Connector yet. We are going to
   * pretend that it is working and just make every option except row empty for now. Once segments are supported we
   * will be able to add real support for them inside this method. Without segments it is impossible to retrieve row,
   * rowsUpdated, error and Warnings at the same time, currently it can only retrieve one of those 4 since Result
   * doesn't allow asynchronously subscribing to more than one field.
   * <p>
   * Here is explanation for why result cannot allow subscribing to both getRowsUpdated and .map() at the same time:
   * https://github.com/pgjdbc/r2dbc-postgresql/issues/415
   * Here is the git pull request which added segments as a way to solve the problem outlined above.
   * was accepted and merged into R2DBC.
   * https://github.com/r2dbc/r2dbc-spi/pull/215/files
   * <p>
   * As of MariaDbR2DBC ver. 1.0.3 If you check MariaDbResult implementation of Result, it does not support flatMap() or
   * any of the other extra methods of Result. It only implements map() and getRowsUpdated().
   * In order to add support for this, we will go ahead and create a fork of MariaDBR2DBC/Connector and work to implement
   * Segments. But until that is finished we will continue to pretend segments is working, but just support rows until
   * the fork is finished.
   */
  public static ReactiveMono<DbResult> getDbResult(Result result) {
/* KEEP THIS HERE:
    resultMono.map(result1 -> result1.flatMap(segment -> {
        if (segment instanceof Result.RowSegment) {
        } else if (segment instanceof Result.OutSegment) {
        } else if (segment instanceof Result.UpdateCount) {
        } else if (segment instanceof Result.Message) {
          final Result.Message message = (Result.Message) segment;
          message.message();
          message.errorCode();
          message.exception();
          message.sqlState();
        }
        return null;
    }));
*/
    //FIXME: Remove the access of getRowsUpdated from this method. Instead make rowsUpdated Optional<Integer>, and make ErrorMessage and
    // WarningMessage Optionals until we actually get Segments working. In which case we'll use something like the code above to
    // retrieve segments from flatmap of Result.
    return ReactiveMono.make(Flux.from(
            result.map(
                (row, rowMetadata) -> {
                  final List<String> names = rowMetadata
                      .getColumnMetadatas()
                      .stream()
                      .map(ReadableMetadata::getName)
                      .toList();
                  IList<Object> rowValues = IArrayList.empty;
                  IList<String> colNames = IArrayList.empty;
                  for (int i = 0; i < names.size(); i++) {
                    rowValues = rowValues.add(row.get(i));
                    colNames = colNames.add(names.get(i));
                  }
                  return new rowColData(rowValues, colNames);
                }))
        .collectList()
        .filter(rowList -> rowList.size() > 0)
        .map(rowList -> {
          final IList<String> columnNames = rowList.get(0).colNames;
          final IList<String> warningList = IArrayList.empty;
          final IList<String> errorList = IArrayList.empty;
          IList<Object> allRowData = IArrayList.empty;
          for (rowColData rowColData : rowList) {
            allRowData = allRowData.addAll(rowColData.rowValues);
          }
          final ITable iTableResult = ITable.make(columnNames, allRowData.toArray());
          return DbResult.make(iTableResult, errorList, warningList, 0);
        })
        .defaultIfEmpty(DbResult.empty));
  }

  public ReactiveMono<DatabaseContainer> getDb(String name) {
    Database database = Database.make(name);
    return ReactiveMono.make(
        dbExists(name)
            .unwrapMono()
            .filter(aBoolean -> aBoolean)
            .flatMap(e -> execute("show create database " + name)
                .unwrapMono())
            .flatMap(result ->
                Flux.just(result.getColumn("create database"))
                    .next()
                    .flatMap(dbCreateStr -> execute("use " + name + "; show tables")
                        .unwrapFlux()
                        .flatMap(result1 -> {
                          return Flux.just(result1.getColumn("tables_in_" + name));
                        })
                        .collectList()
                        .flatMap(tableNameList -> {
                          final IList<String> tableNameIList = IArrayList.make(tableNameList.toArray(new String[0]));
                          return convertToDbContainer(tableNameIList, name, database).unwrapMono();
                        })
                        .cast(DatabaseContainer.class)
                    )
            )
            .defaultIfEmpty(DatabaseContainer.empty)
    );
  }

  private ReactiveMono<Boolean> dbExists(String name) {
    return ReactiveMono.make(execute("show databases")
        .unwrapMono()
        .flatMapMany(dbResult -> Flux.just(dbResult.getColumn("database")))
        .cast(String.class)
        .filter(dbName -> dbName.equals(name))
        .next()
        .map(dbName -> true)
        .defaultIfEmpty(false));
  }

  @Override
  public <T extends DbRecord, I extends DbRecordId> I save(T record) {
    return null;
  }

  @NotNull
  private ReactiveMono<DatabaseContainer> convertToDbContainer(IList<String> tblNameList, String dbName,
                                                               Database database) {
    return ReactiveMono.make(Flux.fromIterable(tblNameList)
        .flatMap(tblName -> execute("use " + dbName + "; show create table " + generator.checkToAddBackticks(
            tblName)).unwrapFlux()) //FIXME: Figure out a way to support .flatMap with ReactiveFlux without needing to unrwapFlux.
//        .log()
        .flatMap(dbResult -> Flux.just(dbResult.getColumn("create table")))
        .map(tblCreateStr -> generator.getTableFromSql((String) tblCreateStr, database))
        .collectList()
        .map(tableContainers -> (IList<TableContainer>) (IList) IArrayList.make(tableContainers.toArray()))
        .map(tables -> convertListToIMap(tables))
        .map(tablesIList -> DatabaseContainer.make(database, tablesIList)));
  }

  private IMap<String, TableContainer> convertListToIMap(IList<TableContainer> tables) {
    IMap<String, TableContainer> map = IArrayMap.empty;
    for (TableContainer table : tables) {
      map = map.put(table.table.tableName, table);
    }
    return map;
  }

}
