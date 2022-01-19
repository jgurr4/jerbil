package com.ple.jerbil.data;

import com.ple.jerbil.data.query.CompleteQuery;
import com.ple.jerbil.data.query.CreateQuery;
import com.ple.jerbil.data.query.QueryList;
import com.ple.jerbil.data.query.Table;
import com.ple.util.IArrayList;
import com.ple.util.IList;
import org.jetbrains.annotations.Nullable;

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
    if (errorMessage != null) {
      return true;
    }
    return false;
  }

  public boolean firstTimeGenerated() {
    if (schemaType != null) {
      if (schemaType == SchemaType.generated) {
        return true;
      }
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

  public Database sync() {
    return hasError() ? this : sync(DdlOption.update);
  }

  public Database sync(DdlOption ddlOption) {
    if (hasError()) {
      return this;
    }
    if (ddlOption == DdlOption.create) {
      return createSchema(Create.shouldNotDrop).checkDbStructure();//.checkTableStructure();
    } else if (ddlOption == DdlOption.update) {
    } else if (ddlOption == DdlOption.replace) {
    } else if (ddlOption == DdlOption.replaceDrop) {
    }
    return this;
  }

  private Database createSchema(Create createOption) {
    if (hasError()) {
      return this;
    }
    if (createOption == Create.shouldDrop) {
      DataGlobal.bridge.execute("drop database " + name);
    }
    final Database db = DataGlobal.bridge.execute("show databases;")
      .flatMap(result -> result.map((row, rowMetadata) -> row.get("database")))
      .filter(dbName -> dbName.equals(name))
      .take(1)
      .map(e -> make(name, tables, null, SchemaType.reused))
      .switchIfEmpty(DataGlobal.bridge.execute(createAll().toSql())
        .doOnSubscribe((e) -> System.out.println("(DB Sync): Auto-generated schema: `" + name + "`"))
        .map(e -> make(name, tables, null, SchemaType.generated)))
//      .onErrorContinue((err, type) -> make(name, tables, true, err.getMessage())) //FIXME: Find out how to return database object with error message by catching error in stream rather than throwing error.
      .blockLast();
    if (!db.firstTimeGenerated()) {
      System.out.println("(DB Sync): Re-using existing schema: `" + name + "`");
    }
    return db;
  }

  // FIXME: After getting IArrayList working replace ArrayList and try to do everything inside streams instead of using
  // iterations.
  private Database checkDbStructure() {
    if (hasError() || firstTimeGenerated()) {
      return this;
    }
    IList<String> tableList = IArrayList.make();
    for (Table table : tables) {
      tableList = tableList.add(table.name);
    }
    final List<String> schemaTables = DataGlobal.bridge.execute("use " + name + "; show tables")
      .flatMap(result -> result.map((row, rowMetadata) -> (String) row.get("tables_in_" + name)))
      .collectList()
      .block();
    final IList<String> finalTableList = tableList;
    final List<String> finalSchemaTables = schemaTables;
    for (String schemaTable : finalSchemaTables) {
      tableList = tableList.remove(schemaTable);
    }
    for (String table : finalTableList) {
      schemaTables.remove(table);
    }
    for (String schemaTable : schemaTables) {
      System.out.println("Extra table `" + schemaTable + "` exists in database: `" + name + "`");
    }
    for (String table : tableList) {
      System.out.println("Missing table `" + table + "` in database: `" + name + "`");
    }
    if (schemaTables.size() > 0 || tableList.length() > 0) {
//      System.out.println("\n\t[ERROR]: diffs exist between schema object and the database called `" + name + "`. \n\tDdlOption.create cannot make modifications when there are diffs.");
//      System.exit(1);
      return make(name, tables, "\n[ERROR]: diffs exist between schema object and the database called `" + name + "`. \n\tDdlOption.create cannot make modifications when there are diffs.", schemaType);
    }
    return this;
  }

  private Database checkTableStructure() {
    if (hasError() || firstTimeGenerated()) {
      return this;
    }
    return this;
  }

  private Database updateSchemaStructure() {
    if (hasError() || firstTimeGenerated()) {
      return this;
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
    return this;
  }

  private Database updateTableStructure() {
    if (hasError() || firstTimeGenerated()) {
      return this;
    }
    for (Table table : tables) {
      DataGlobal.bridge.execute("use test; show create table" + table)
        .flatMap(result -> result.map((row, rowMetadata) -> row.get("create table")))
        .subscribe();
    }
    return this;
  }


}
