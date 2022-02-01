package com.ple.jerbil.data;

import com.ple.jerbil.data.query.CompleteQuery;
import com.ple.jerbil.data.query.CreateQuery;
import com.ple.jerbil.data.query.QueryList;
import com.ple.jerbil.data.query.Table;
import com.ple.util.IArrayList;
import com.ple.util.IList;
import org.jetbrains.annotations.Nullable;
import reactor.core.publisher.Mono;

/**
 * Database is a object representing the database and it's tables.
 */
@DelayedImmutable
public class Database {

  public final String name;
  @Nullable
  public IList<Table> tables;

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

  public Mono<SyncResult> sync() {
    return sync(DdlOption.make((byte) 0b110));
  }

  public Mono<SyncResult> sync(DdlOption ddlOption) {
    return Mono.just(SyncResult.make(this));
  }

  @Override
  public String toString() {
    return "Database{" +
      "name='" + name + '\'' +
      ", tables=" + tables +
      '}';
  }

}
