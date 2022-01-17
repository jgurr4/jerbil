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
  @Nullable public IList<Table> tables;

  public Database(String name, @Nullable IList<Table> tables) {
    this.name = name;
    this.tables = tables;
  }

  public static Database make(String name) {
    return new Database(name, null);
  }

  public Database add(Table... tables) {
    return new Database(name, IArrayList.make(tables));
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


  public void sync() {
    sync(DdlOption.update);
  }
  
  public void sync(DdlOption ddlOption) {
    if (ddlOption == DdlOption.create) {
      createSchema(Create.shouldNotDrop);
      checkSchemaStructure();
      checkTableStructure();
    } else if (ddlOption == DdlOption.update) {
    } else if (ddlOption == DdlOption.replace) {
    } else if (ddlOption == DdlOption.replaceDrop) {
    }
  }

  private enum Create {
    shouldDrop,
    shouldNotDrop
  }

  private void createSchema(Create createOption) {
    if (createOption == Create.shouldDrop) {
      DataGlobal.bridge.execute("drop database " + name);
    }
    DataGlobal.bridge.execute("show databases;")
      .flatMap(result -> result.map((row, rowMetadata) -> row.get("database")))
//      .log()
      .filter(dbName -> dbName == name)
      .doOnNext(dbName -> System.out.println(dbName))
      .switchIfEmpty(DataGlobal.bridge.execute(createAll().toSql()))
      .subscribe();
  }

  private void checkTableStructure() {
    
  }

  private void checkSchemaStructure() {

  }

  private void updateSchemaStructure() {
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
  }

  private void updateTableStructure() {
    for (Table table : tables) {
    DataGlobal.bridge.execute("use test; show create table" + table)
      .flatMap(result -> result.map((row, rowMetadata) -> row.get("create table")))
      .subscribe();
    }

  }


}
