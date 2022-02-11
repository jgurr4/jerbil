package com.ple.jerbil.data;

import com.ple.jerbil.data.bridge.ReactiveWrapper;
import com.ple.jerbil.data.bridge.ReactorMono;
import com.ple.jerbil.data.bridge.SynchronousObject;
import com.ple.jerbil.data.query.CompleteQuery;
import com.ple.jerbil.data.query.CreateQuery;
import com.ple.jerbil.data.query.QueryList;
import com.ple.jerbil.data.query.Table;
import com.ple.jerbil.data.sync.DbDiff;
import com.ple.jerbil.data.sync.DdlOption;
import com.ple.jerbil.data.sync.DiffService;
import com.ple.jerbil.data.sync.SyncResult;
import com.ple.util.IArrayList;
import com.ple.util.IList;
import org.jetbrains.annotations.Nullable;
import reactor.core.publisher.Mono;

/**
 * Database is a object representing the database and it's tables.
 */
@Immutable
public class Database implements DatabaseContainer {

  public final String name;
  @Nullable
  public final IList<Table> tables;

  public Database(String name, @Nullable IList<Table> tables) {
    this.name = name;
    this.tables = tables;
  }

  public static Database make(String name) {
    return new Database(name, null);
  }

  public static Database make(String name, IList<Table> tables) {
    return new Database(name, tables);
  }

  public Database add(Table... tables) {
    return new Database(name, IArrayList.make(tables));
  }

  public QueryList createAll() {
    QueryList<CompleteQuery> completeQueries = QueryList.make(CreateQuery.make(this));
    for (Table table : tables) {
      completeQueries = completeQueries.add(CreateQuery.make(table));
    }
    return completeQueries;
  }

  public SyncResult sync() {
    return sync(DdlOption.make((byte) 0b110));
  }

  public SyncResult sync(DdlOption ddlOption) {
    ReactiveWrapper<Database> existingDb = getDb(name);
    ReactiveWrapper<DbDiff> dbDiff = DiffService.compareDatabases(this.wrap(), existingDb);
    ReactiveWrapper<DbDiff> filteredDiff = ReactorMono.make(dbDiff.unwrapMono().map(diffs -> diffs.filter(ddlOption)));
    ReactiveWrapper<String> sql = ReactorMono.make(filteredDiff.unwrapMono().map(fDiff -> fDiff.toSql()));
    return SyncResult.make(DataGlobal.bridge.execute(sql), dbDiff);
    //Consider making an executeAll() method that executes each statement individually and returns a flux of results.
    // If one statement has a error then it should prevent further statements.
//    return SyncResult.compare(this, existingDb).filter(ddlOption).toSql().execute();
  }

  public static ReactiveWrapper<Database> getDb(String name) {
    return DataGlobal.bridge.getDb(name);
  }

  @Override
  public String toString() {
    return "Database{" +
      "name='" + name + '\'' +
      ", tables=" + tables +
      '}';
  }

  public String drop() {
    return null;
  }

  public ReactiveWrapper<Database> wrap() {
    return SynchronousObject.make(this);
  }

  @Override
  public Database getDatabase() {
    return this;
  }

}
