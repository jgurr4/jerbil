package com.ple.jerbil.data;

import com.ple.jerbil.data.query.CompleteQuery;
import com.ple.jerbil.data.query.CreateQuery;
import com.ple.jerbil.data.query.QueryList;
import com.ple.jerbil.data.query.Table;
import com.ple.util.IArrayList;
import com.ple.util.IList;
import org.jetbrains.annotations.Nullable;

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
    if (ddlOption == DdlOption.validate) {
      // This checks if database actually matches current schema. If not it throws an error and exits the application.
      throw new RuntimeException("Schema object is not matching the current Schema inside database.");
    } else if (ddlOption == DdlOption.update) {
      // This checks if database actually matches current schema. If not, it will update and change structure but will 
      // never drop or remove databases/tables/columns, but it may modify columns/tables. If the database or table
      // does not exist, it will also create them based on schema object.
    } else if (ddlOption == DdlOption.create) {
      // This creates the schema, destroying previous data.
    } else if (ddlOption == DdlOption.createDrop) {
      // This creates the schema, then drops it at the end of the session.
    }
  }
  
  /**
   * This will check the current database structure before running the statement. It makes sure
   * the relevant databases and tables exist. If anything doesn't exactly match the structure of the query
   * then this method will execute certain commands to recreate/update the database structure. If a database
   * doesn't exist, then this method will create the database and tables.
   * @param query
   */
  private void checkDbStructure(CompleteQuery query) {
//    this.execute("show databases;")
//      .blockLast()
//      .map((row, rowMetadata) -> row.get("Database"))
//      .subscribe(result -> result == query);
//    this.execute("use test;")
//    this.execute("show tables;")
  }

  /**
   * This will check the current table structure of the relevant tables. It makes sure the
   * tables have the right amount/type of columns. If anything doesn't exactly match the structure of the query
   * then this method will execute certain commands to alter the table structure. If a table doesn't exist, then
   * this will create the table.
   * @param query
   */
  private void checkTableStructure(CompleteQuery query) {
//    this.execute("use test;")
//    this.execute("show create table" + table)

  }


}
