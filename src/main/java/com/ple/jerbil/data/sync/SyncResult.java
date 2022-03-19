package com.ple.jerbil.data.sync;

import com.ple.jerbil.data.DbResult;
import com.ple.util.Immutable;
import com.ple.jerbil.data.reactiveUtils.ReactiveWrapper;
import io.r2dbc.spi.Result;

import java.util.Objects;

@Immutable
public class SyncResult<D extends Diff> {

  public final ReactiveWrapper<DbResult> result;
  public final D diff;

  protected SyncResult(ReactiveWrapper<DbResult> result, D diff) {
    this.result = result;
    this.diff = diff;
  }

  public static <D extends Diff> SyncResult make(ReactiveWrapper<DbResult> result, D diffs) {
    return new SyncResult(result, diffs);
  }

  @Override
  public String toString() {
    return "SyncResult{" +
      "result=" + result +
      ", diffs=" + diff +
      '}';
  }
/*
  protected Mono<SyncResult> createSchema(Create createOption) {
    if (hasError()) {
      return Mono.just(this);
    }
//    if (createOption == Create.shouldDrop) {
//      DataGlobal.bridge.execute("drop database " + name).subscribe();
//    }
    return DataGlobal.bridge.execute("show databases;")
      .flatMap(result -> result.map((row, rowMetadata) -> row.get("database")))
      .filter(dbName -> dbName.equals(db.name))
      .next()
      .map(database -> make(db, null, GeneratedType.reused))
      .doOnNext((e) -> System.out.println("(DB Sync): Re-using existing schema: `" + db.name + "`"))
      .switchIfEmpty(DataGlobal.bridge.execute(db.createAll().toSql())
        .doOnSubscribe((e) -> System.out.println("(DB Sync): Auto-generated schema: `" + db.name + "`"))
        .map(e -> make(db, null, GeneratedType.generated)).next());
//      .onErrorContinue((err, type) -> make(name, tables, true, err.getMessage())) //FIXME: Find out how to return database object with error message by catching error in stream rather than throwing error.
  }

  protected Mono<SyncResult> checkDbStructure() {
    return checkDbStructure(DdlOption.create);
  }

  protected Mono<SyncResult> checkTableStructure() {
    return checkTableStructure(DdlOption.create);
  }

  private Mono<SyncResult> checkDbStructure(DdlOption ddlOption) {
    if (hasError() || firstTimeGenerated()) {
      return Mono.just(this);
    }
    IList<String> tableList = IArrayList.make();
    if (db.tables != null) {
      for (Table table : db.tables) {
        tableList = tableList.add(table.name);
      }
    }
    final IList<String> finalTableList = tableList;
    return DataGlobal.bridge.execute("use " + db.name + "; show tables")
      .flatMap(result -> result.map((row, rowMetadata) -> (String) row.get("tables_in_" + db.name)))
      .filter(tableName -> !finalTableList.contains(tableName))
      .doOnNext(tableName -> System.out.println("Extra table `" + tableName + "` found in local/remote database: `" + db.name + "`"))
      .map(tableName -> this)
      .filter(db -> {
        if (ddlOption == DdlOption.update) {
          return false;
        }
        return true;
      })
      .switchIfEmpty(DataGlobal.bridge.execute("use " + db.name + "; show tables")
        .flatMap(result -> result.map((row, rowMetadata) -> (String) row.get("tables_in_" + db.name)))
        .collectList()
        .flatMap(listOfTables -> Flux.just(finalTableList.toArray())
          .filter(tableName -> !listOfTables.contains(tableName))
          .doOnNext(tableName -> {
            if (ddlOption == DdlOption.update) {
              generateMissingTable(tableName);
            } else {
              System.out.println("Missing table `" + tableName + "` in database: `" + db.name + "`");
            }
          })
          .next()
          .map(tableName -> {
            if (ddlOption == DdlOption.update) {
              return make(db, null, GeneratedType.modified);
            } else if (ddlOption == DdlOption.create) {
              System.out.println("\n[WARNING]: diffs exist between Database Object and the local/remote database called `" + db.name + "`. \n\tDdlOption.create cannot make modifications when there are diffs.\n");
            }
            return this;
          }))
        .defaultIfEmpty(this))
      .next();
  }

  private void generateMissingTable(String tableName) {
    Flux.just(db.tables.toArray())
      .filter(table -> table.name.equals(tableName))
//      .doOnNext(System.out::println)  FIXME: Replace this with logger once I get one.
      .next()
      .doOnNext(table -> DataGlobal.bridge.execute("use " + db.name + "; " + table.toSql()).subscribe())
      .doOnNext(table -> System.out.println("Successfully generated missing table: `" + table.name + "`"))
      .subscribe();
  }

  private Mono<SyncResult> checkTableStructure(DdlOption ddlOption) {
    if (hasError() || firstTimeGenerated()) {
      return Mono.just(this);
    }
    return DataGlobal.bridge.execute("use " + db.name + "; show tables")
      .flatMap(result -> result.map((row, rowMetadata) -> (String) row.get("tables_in_" + db.name)))
      .flatMap(tableName -> DataGlobal.bridge.execute("use " + db.name + "; show create table " + tableName)
        .flatMap(result -> result.map((row, rowMetadata) -> (String) row.get("create table")))
        .map(createTableString -> Table.fromSql(createTableString))
        .flatMap(existingTable -> compareTable(existingTable, ddlOption)))
      .next()
      .defaultIfEmpty(this);
  }

  private class ErrorHolder {
    public final String errorMessage;
    public final Table table;
    public ErrorHolder(String errorMessage, Table table) {
      this.errorMessage = errorMessage;
      this.table = table;
    }
  }

  private Mono<SyncResult> compareTable(Table existingTable, DdlOption ddlOption) {
    if (hasError()) {
      return Mono.just(this);
    }
    if (db.tables != null) {
      final Flux<Column> columnFlux = Flux.just(db.tables.toArray())
        .filter(table -> table.name.equals(existingTable.name))
        .next()
        .doOnNext(table -> {
          if (table == null) {
            System.out.println("An extra table was found inside the local/remote database that has not been specified in the Database Object: \n" + existingTable.toString());
          }
        })
        .filter(table -> table.engine.name().equals(existingTable.engine.name()))
        .map(table -> {
          if (table == null && ddlOption == DdlOption.create) {
            return new ErrorHolder("Engine of " + existingTable.name + " inside local/remote database is: " + existingTable.engine.name() + ". This does not match data inside the Database Object.", table);
          }
          return new ErrorHolder(null, table);
        })
        .flatMapMany(errorHolder -> Flux.just(errorHolder.table.columns.toArray())
          .filter(column -> !existingTable.columns.contains(column))
          .doOnNext(column -> System.out.println("\n[WARNING]: This column: \n" + column.toString() + "\ndoes not match any columns found inside the table in the local/remote database:\n" + existingTable.toString().replaceAll(", Column\\{", "\nColumn{").replaceFirst("\\{values=\\[Column", "{values=[\nColumn")))
          .next());
      if (ddlOption == DdlOption.update) {
        return columnFlux.flatMap(column -> alterTable(existingTable, column))
          .filter(Boolean.FALSE::equals)
          .next()
          .map(bool -> make(db, "[ERROR]: Failed to generate a missing column in table:\n" + existingTable, GeneratedType.modified))
          .defaultIfEmpty(make(db, , GeneratedType.modified));
      } else if (ddlOption == DdlOption.create) {
        return columnFlux
          .next()
          .map(column -> make(db, "\n[ERROR]: Some tables are missing from the local/remote database called `" + db.name + "`. \n\tDdlOption.create cannot make modifications when there are diffs.\n", generatedType))
          .defaultIfEmpty(this);
      }
    }
    return Mono.just(make(db, "[ERROR]: The tables inside Database Object is null.", generatedType));
  }

  private Mono<Boolean> alterTable(Table existingTable, Column column) {
    System.out.println("use test; alter table " + existingTable.name + " add column " + column.toSql());
    return DataGlobal.bridge.execute("use test; alter table " + existingTable.name + " add column " + column.toSql())
      .flatMap(Result::getRowsUpdated)
      .next()
      .doOnNext(numRows -> System.out.println("Successfully generated column: " + column))
      .hasElement();
  }

  protected Mono<SyncResult> updateSchemaStructure() {
    if (hasError() || firstTimeGenerated()) {
      return Mono.just(this);
    }
    return checkDbStructure(DdlOption.update);
  }

  protected Mono<SyncResult> updateTableStructure() {
    if (hasError() || firstTimeGenerated()) {
      return Mono.just(this);
    }
    return checkTableStructure(DdlOption.update);
  }
*/

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof SyncResult)) return false;
    SyncResult<?> that = (SyncResult<?>) o;
    return result.equals(that.result) && diff.equals(that.diff);
  }

  @Override
  public int hashCode() {
    return Objects.hash(result, diff);
  }
}