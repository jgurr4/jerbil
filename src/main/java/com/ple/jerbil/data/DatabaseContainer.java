package com.ple.jerbil.data;

import com.ple.jerbil.data.reactiveUtils.ReactiveWrapper;
import com.ple.jerbil.data.reactiveUtils.SynchronousObject;
import com.ple.jerbil.data.query.*;
import com.ple.jerbil.data.sync.DbDiff;
import com.ple.jerbil.data.sync.DdlOption;
import com.ple.jerbil.data.sync.DiffService;
import com.ple.jerbil.data.sync.SyncResult;
import com.ple.util.IArrayMap;
import com.ple.util.IEntry;
import com.ple.util.IMap;
import reactor.util.annotation.Nullable;

public class DatabaseContainer {

  public static final DatabaseContainer empty = new DatabaseContainer(Database.make("none"), IArrayMap.empty, null);
  public final Database database;
  public final IMap<String, TableContainer> tables;
  @Nullable public final CharSet charSet;

  protected DatabaseContainer(Database database, IMap<String, TableContainer> tables,
                              @Nullable CharSet charSet) {
    this.database = database;
    this.tables = tables;
    this.charSet = charSet;
  }

  public static DatabaseContainer make(Database database, IMap<String, TableContainer> tables) {
    return new DatabaseContainer(database, tables, null);
  }

  /**
   * This will sync a Database using a default filtering setting of Create and Update. It won't delete any extra tables or columns.
   * @return SyncResult
   */
  public SyncResult sync() {
    return sync(DdlOption.make().create().update());
  }

  // This method is for convenience, it automatically retrieves a database object from rdbms which has the same name.
  public SyncResult sync(DdlOption ddlOption) {
//    ReactiveWrapper<DatabaseContainer> existingDb = getDbContainer(this.database.databaseName);
//    ReactiveWrapper<DbDiff> dbDiff = DiffService.compareDatabases(SynchronousObject.make(this), existingDb);
//    ReactiveWrapper<DbDiff> filteredDiff = dbDiff.map(diffs -> diffs.filter(ddlOption));
//    return filteredDiff.map(fDiff -> fDiff.toSql()).map(sql ->
//    SyncResult.make(DataGlobal.bridge.execute(sql), dbDiff));
    //TODO: Consider making an executeAll() method that executes each statement individually and returns a flux of results.
    // If one statement has a error then it should prevent further statements.
/*
    return getDbContainer(database.databaseName)
        .map(existingDb -> DiffService.compareDatabases(this, existingDb))
        .map(dbDiff -> SyncRaft.make(dbDiff.filter(ddlOption), dbDiff))
        .map(syncRaft -> SyncRaft.make(syncRaft.filteredDiff.toSql(), syncRaft.dbDiff))
        .map(syncRaft -> SyncResult.make(DataGlobal.bridge.execute(syncRaft.sql), sync().diff))
*/
    return null;
  }

  // This method is when you already have two databaseContainer objects ready to compare.
  public SyncResult sync(DatabaseContainer db, DdlOption ddlOption) {
    return null;
  }

  public QueryList createAll() {
    QueryList<CompleteQuery> completeQueries = QueryList.make(CreateQuery.make(database));
    for (IEntry<String, TableContainer> entry : tables) {
      completeQueries = completeQueries.add(CreateQuery.make(entry.value));
    }
    return completeQueries;
  }

  public DatabaseContainer add(TableContainer... newTables) {
    IMap<String, TableContainer> newTablesMap = tables;
    for (TableContainer t : newTables) {
     newTablesMap = newTablesMap.put(t.table.tableName, t);
    }
    return new DatabaseContainer(database, newTablesMap, charSet);
  }

  public static ReactiveWrapper<DatabaseContainer> getDbContainer(String name) {
    return DataGlobal.bridge.getDb(name);
  }

  public ReactiveWrapper<DatabaseContainer> wrap() {
    return SynchronousObject.make(this);
  }

  public String drop() {
    return null;
  }

  @Override
  public String toString() {
    return "DatabaseContainer{" +
        "database=" + database +
        ", tables=" + tables +
        ", charSet=" + charSet +
        '}';
  }
}
