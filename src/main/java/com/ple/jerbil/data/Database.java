package com.ple.jerbil.data;

import com.ple.jerbil.data.query.CompleteQuery;
import com.ple.jerbil.data.query.CreateQuery;
import com.ple.jerbil.data.query.QueryList;
import com.ple.jerbil.data.query.Table;
import com.ple.util.IArrayList;
import com.ple.util.IList;
import org.jetbrains.annotations.Nullable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Database is a object representing the database and it's tables.
 */
@DelayedImmutable
public class Database {

  public final String name;
  @Nullable
  public IList<Table> tables;
  @Nullable
  public final String errorMessage;
  @Nullable
  public final GeneratedType generatedType;

  public Database(String name, @Nullable IList<Table> tables, @Nullable String errorMessage, @Nullable GeneratedType generatedType) {
    this.name = name;
    this.tables = tables;
    this.errorMessage = errorMessage;
    this.generatedType = generatedType;
  }

  public static Database make(String name) {
    return new Database(name, null, null, null);
  }

  public static Database make(String name, IList<Table> tables, String errorMessage, GeneratedType generatedType) {
    return new Database(name, tables, errorMessage, generatedType);
  }

  public Database add(Table... tables) {
    return new Database(name, IArrayList.make(tables), null, null);
  }

  public boolean hasError() {
    return errorMessage != null;
  }

  public boolean firstTimeGenerated() {
    if (generatedType != null) {
      return generatedType == GeneratedType.generated;
    }
    return false;
  }

  public QueryList createAll() {
    QueryList<CompleteQuery> completeQueries = QueryList.make(CreateQuery.make(this));
    for (Table table : tables) {
      completeQueries = completeQueries.add(CreateQuery.make(table));
    }
    return completeQueries;
  }

  public Mono<Database> sync() {
    return hasError() ? Mono.just(this) : sync(DdlOption.update);
  }

  public Mono<Database> sync(DdlOption ddlOption) {
    if (hasError()) {
      return Mono.just(this);
    }
    if (ddlOption == DdlOption.create) {
      return createSchema(Create.shouldNotDrop)
        .flatMap(Database::checkDbStructure)
        .flatMap(Database::checkTableStructure);
    } else if (ddlOption == DdlOption.update) {
      return createSchema(Create.shouldNotDrop)
        .flatMap(Database::updateSchemaStructure)
        .flatMap(Database::updateTableStructure);
    } else if (ddlOption == DdlOption.replace) {
      return createSchema(Create.shouldDrop);
    } else if (ddlOption == DdlOption.replaceDrop) {
      //TODO: Make it so that when the program exits, a command is run likely inside the MariadbR2dbcBridge which drops the database.
      return createSchema(Create.shouldDrop); //.drop();
    }
    return Mono.just(this);
  }

  private Mono<Database> createSchema(Create createOption) {
    if (hasError()) {
      return Mono.just(this);
    }
    if (createOption == Create.shouldDrop) {
      DataGlobal.bridge.execute("drop database " + name);
    }
    return DataGlobal.bridge.execute("show databases;")
      .flatMap(result -> result.map((row, rowMetadata) -> row.get("database")))
      .filter(dbName -> dbName.equals(name))
      .next()
      .map(db -> make(name, tables, null, GeneratedType.reused))
      .doOnNext((e) -> System.out.println("(DB Sync): Re-using existing schema: `" + name + "`"))
      .switchIfEmpty(DataGlobal.bridge.execute(createAll().toSql())
        .doOnSubscribe((e) -> System.out.println("(DB Sync): Auto-generated schema: `" + name + "`"))
        .map(e -> make(name, tables, null, GeneratedType.generated)).next());
//      .onErrorContinue((err, type) -> make(name, tables, true, err.getMessage())) //FIXME: Find out how to return database object with error message by catching error in stream rather than throwing error.
  }

  private Mono<Database> checkDbStructure() {
    return checkDbStructure(null);
  }

  private Mono<Database> checkDbStructure(DdlOption ddlOption) {
    if (hasError() || firstTimeGenerated()) {
      return Mono.just(this);
    }
    IList<String> tableList = IArrayList.make();
    if (tables != null) {
      for (Table table : tables) {
        tableList = tableList.add(table.name);
      }
    }
    final IList<String> finalTableList = tableList;
    return DataGlobal.bridge.execute("use " + name + "; show tables")
      .flatMap(result -> result.map((row, rowMetadata) -> (String) row.get("tables_in_" + name)))
      .filter(tableName -> !finalTableList.contains(tableName))
      .doOnNext(tableName -> System.out.println("Extra table `" + tableName + "` found in local/remote database: `" + name + "`"))
      .map(tableName -> make(name, tables, "\n[WARNING]: diffs exist between Database Object and the local/remote database called `" + name + "`. \n\tDdlOption.create cannot make modifications when there are diffs.\n", generatedType))
      .filter(db -> {
        if (ddlOption == DdlOption.update) {
          return false;
        }
        return true;
      })
      .switchIfEmpty(DataGlobal.bridge.execute("use " + name + "; show tables")
        .flatMap(result -> result.map((row, rowMetadata) -> (String) row.get("tables_in_" + name)))
        .collectList()
        .flatMap(listOfTables -> Flux.just(finalTableList.toArray())
          .filter(tableName -> !listOfTables.contains(tableName))
          .doOnNext(tableName -> {
            if (ddlOption == DdlOption.update) {
              generateMissingTable(tableName);
            } else {
              System.out.println("Missing table `" + tableName + "` in database: `" + name + "`");
            }
          })
          .next()
          .delayElement(Duration.ofMillis(100)) //FIXME: Find another way of doing this which doesn't force waiting until 100ms has finished.
          .map(tableName -> {
            if (ddlOption == DdlOption.update) {
              return make(name, tables, null, GeneratedType.modified);
            }
            return make(name, tables, "\n[WARNING]: diffs exist between Database Object and the local/remote database called `" + name + "`. \n\tDdlOption.create cannot make modifications when there are diffs.\n", generatedType);
          }))
        .defaultIfEmpty(this))
      .next();
  }

  private void generateMissingTable(String tableName) {
    Flux.just(tables.toArray())
      .filter(table -> table.name.equals(tableName))
//      .doOnNext(System.out::println)  FIXME: Replace this with logger once I get one.
      .next()
      .doOnNext(table -> DataGlobal.bridge.execute("use " + name + "; " + table.toSql()).subscribe())
      .doOnNext(table -> System.out.println("Successfully generated missing table: `" + table.name + "`"))
      .subscribe();
  }

  private Mono<Database> checkTableStructure() {
    if (hasError() || firstTimeGenerated()) {
      return Mono.just(this);
    }
    return DataGlobal.bridge.execute("use " + name + "; show tables")
      .flatMap(result -> result.map((row, rowMetadata) -> (String) row.get("tables_in_" + name)))
      .flatMap(tableName -> DataGlobal.bridge.execute("use " + name + "; show create table " + tableName)
        .flatMap(result -> result.map((row, rowMetadata) -> (String) row.get("create table")))
        .map(createTableString -> Table.fromSql(createTableString))
        .flatMap(existingTable -> compareTable(existingTable)))
      .filter(db -> db.hasError())
      .next()
      .defaultIfEmpty(this);
  }

  private Mono<Database> compareTable(Table existingTable) {
    AtomicReference<String> errorMessage = new AtomicReference<>(null);
    if (hasError()) {
      return Mono.just(this);
    }
    if (tables != null) {
      return Flux.just(tables.toArray())
        .filter(table -> table.name.equals(existingTable.name))
        .next()
        .doOnNext(table -> {
          if (table == null) {
            errorMessage.set("A table inside Database Object is missing from the local/remote database.");
          }
        })
        .filter(table -> table.engine.name().equals(existingTable.engine.name()))
        .doOnNext(table -> {
          if (table == null) {
            errorMessage.set("Engine of " + existingTable.name + " inside local/remote database is: " + existingTable.engine.name() + ". This does not match data inside the Database Object." );
          }
        })
        .flatMapMany(table -> Flux.just(table.columns.toArray()))
        .filter(column -> !existingTable.columns.contains(column))
        .doOnNext(column -> System.out.println("\nThis column: \n" + column.toString() + "\ndoes not match any columns found inside this table:\n" + existingTable.toString().replaceAll(", Column\\{", "\nColumn{").replaceFirst("\\{values=\\[Column", "{values=[\nColumn")))
        .next()
        .map(tableLine -> make(name, tables, "\n[WARNING]: diffs exist in the table structure of some tables between the Database Object and the local/remote database called `" + name + "`. \n\tDdlOption.create cannot make modifications when there are diffs.\n", generatedType))
        .defaultIfEmpty(make(name, tables, errorMessage.get(), generatedType));
    }
    return Mono.just(make(name, tables, "[ERROR]: The tables inside Database Object is null.", generatedType));
  }

  private Mono<Database> updateSchemaStructure() {
    if (hasError() || firstTimeGenerated()) {
      return Mono.just(this);
    }
    return checkDbStructure(DdlOption.update);
  }

  private Mono<Database> updateTableStructure() {
    if (hasError() || firstTimeGenerated()) {
      return Mono.just(this);
    }
    return Mono.just(this);
  }


}
