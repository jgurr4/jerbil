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

import java.util.List;

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
  public final SchemaType schemaType;

  public Database(String name, @Nullable IList<Table> tables, @Nullable String errorMessage, @Nullable SchemaType schemaType) {
    this.name = name;
    this.tables = tables;
    this.errorMessage = errorMessage;
    this.schemaType = schemaType;
  }

  public static Database make(String name) {
    return new Database(name, null, null, null);
  }

  public static Database make(String name, IList<Table> tables, String errorMessage, SchemaType schemaType) {
    return new Database(name, tables, errorMessage, schemaType);
  }

  public Database add(Table... tables) {
    return new Database(name, IArrayList.make(tables), null, null);
  }

  public boolean hasError() {
    return errorMessage != null;
  }

  public boolean firstTimeGenerated() {
    if (schemaType != null) {
      return schemaType == SchemaType.generated;
    }
    return false;
  }

  public String toSql() {
    return null;
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
    } else if (ddlOption == DdlOption.replaceDrop) {
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
      .map(db -> make(name, tables, null, SchemaType.reused))
      .doOnNext((e) -> System.out.println("(DB Sync): Re-using existing schema: `" + name + "`"))
      .switchIfEmpty(DataGlobal.bridge.execute(createAll().toSql())
        .doOnSubscribe((e) -> System.out.println("(DB Sync): Auto-generated schema: `" + name + "`"))
        .map(e -> make(name, tables, null, SchemaType.generated)).next());
//      .onErrorContinue((err, type) -> make(name, tables, true, err.getMessage())) //FIXME: Find out how to return database object with error message by catching error in stream rather than throwing error.
  }

  // FIXME: After getting IArrayList working replace ArrayList and try to do everything inside streams instead of using
  // iterations.
  private Mono<Database> checkDbStructure() {
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
      .doOnNext(tableName -> System.out.println("Extra table `" + tableName + "` exists in database: `" + name + "`"))  //FIXME: Find out why .contains method is not working to filter out the inventory table even though it should.
      .map(tableName -> make(name, tables, "\n[ERROR]: diffs exist between schema object and the database called `" + name + "`. \n\tDdlOption.create cannot make modifications when there are diffs.", schemaType))
      .switchIfEmpty(DataGlobal.bridge.execute("use " + name + "; show tables")
        .flatMap(result -> result.map((row, rowMetadata) -> (String) row.get("tables_in_" + name)))
        .collectList()
        .flatMap(listOfTables -> Flux.just(finalTableList.toArray())
          .filter(tableName -> !listOfTables.contains(tableName))
          .doOnNext(tableName -> System.out.println("Missing table `" + tableName + "` in database: `" + name + "`"))
          .next()
          .map(tableName -> make(name, tables, "\n[ERROR]: diffs exist between schema object and the database called `" + name + "`. \n\tDdlOption.create cannot make modifications when there are diffs.", schemaType)))
        .defaultIfEmpty(this))
      .next();
  }

  //TODO: Ask about if statement. Does it block? Should I use reactive streams to check conditions instead?
  private Mono<Database> checkTableStructure() {
    if (hasError() || firstTimeGenerated()) {
      return Mono.just(this);
    }
    return DataGlobal.bridge.execute("use " + name + "; show tables")
      .flatMap(result -> result.map((row, rowMetadata) -> (String) row.get("tables_in_" + name)))
      .flatMap(tableName -> DataGlobal.bridge.execute("use " + name + "; show create table " + tableName)
        .flatMap(result -> result.map((row, rowMetadata) -> (String) row.get("create table")))
        .flatMap(existingTableInfo -> compareTable(existingTableInfo, tableName)))
      .next();
  }

  private Mono<Database> compareTable(String existingTableInfo, String tableName) {
    final String formattedTableInfo = formatToCompare(existingTableInfo);
    if (tables != null) {
      return Flux.just(tables.toArray())
        .filter(table -> table.name.equals(tableName))
        .next()
        .map(Table::toSql)
        .flatMapMany(this::formatToCompareLines)
        .filter(tableLine -> !formattedTableInfo.contains(tableLine))
        .doOnNext(tableLine -> System.out.println("\nThis line: \"" + tableLine + "\" does not match the line inside this table:\n\"" + formattedTableInfo + "\" from your schema"))
        .next()
        .map(tableLine -> make(name, tables, "\n[ERROR]: diffs exist in the table structure of some tables between the schema object and the database called `" + name + "`. \n\tDdlOption.create cannot make modifications when there are diffs.", schemaType))
        .defaultIfEmpty(this);
    }
    return Mono.just(make(name, tables, "[ERROR]: The tables inside Database Object is null.", schemaType));
  }

  private String formatToCompare(String tableInfo) {
    return tableInfo
      .toLowerCase()
      .replaceAll("`", "")
      .replaceAll(" int([0-9].*) ", " int ")
      .replaceAll(" varchar([0-9].*) ", " varchar ")
      .replaceAll("\nprimary.*\n", "")
      .replaceAll("\nkey.*\n", "");
  }

  private Flux<String> formatToCompareLines(String tableInfo) {
    return Flux.just(tableInfo.split("\n"));
  }

/* Change this to work for updating and altering tables.
  private Mono<Database> compareLines(String existingTableInfo) {
    if (tables != null) {
      return Flux.just(tables.toArray())
        .flatMap(table -> formatToCompare(table.toSql()))
        .log()
        .flatMap(tableLine -> Flux.from(formatToCompare(existingTableInfo))
          .log()
          .filter(existingTableLine -> existingTableLine.contains(tableLine))
          .doOnNext(existingTableLine -> System.out.println("This line \"" + existingTableLine + "\" does not match the line: \"" + tableLine + "\" from your schema"))
        )
        .next()
        .map(existingTableLine -> make(name, tables, "\n[ERROR]: diffs exist between table structure of some tables between the schema object and the database called `" + name + "`. \n\tDdlOption.create cannot make modifications when there are diffs.", schemaType))
        .defaultIfEmpty(this);
    }
    return Mono.just(make(name, tables, "[ERROR]: The tables inside Database Object is null.", schemaType));
  }
*/

  private Mono<Database> updateSchemaStructure() {
    if (hasError() || firstTimeGenerated()) {
      return Mono.just(this);
    }
    Boolean dbExists = false;
    DataGlobal.bridge.execute("show databases;")
      .flatMap(result -> result.map((row, rowMetadata) -> row.get("Database")))
      .filter(el -> el == name)
      .switchIfEmpty(DataGlobal.bridge.execute("create database" + name))
      //TODO: Consider printing out that a database was created here if rowsupdated had values.
      .subscribe();
    final IList<Table> schemaTableList = tables;
    final List<String> existingTablesList = DataGlobal.bridge.execute("use " + name + " show tables")
      .flatMap(result -> result.map((row, rowMetadata) -> (String) row.get("tables_in_" + name)))
      .collectList().block();
    for (Table table : tables) {
      if (existingTablesList.contains(table.name)) {
        schemaTableList.remove(table); // TODO: Run this and make sure the IList.remove() method works correctly.
      }
    }
    for (Table table : schemaTableList) {
      DataGlobal.bridge.execute(table.toSql()).subscribe(); //TODO: Run this and make sure the table.toSql works correctly
    }
    return Mono.just(this);
  }

  private Mono<Database> updateTableStructure() {
    if (hasError() || firstTimeGenerated()) {
      return Mono.just(this);
    }
    for (Table table : tables) {
      DataGlobal.bridge.execute("use test; show create table" + table)
        .flatMap(result -> result.map((row, rowMetadata) -> row.get("create table")))
        .subscribe();
    }
    return Mono.just(this);
  }


}
