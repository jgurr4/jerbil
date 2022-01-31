package com.ple.jerbil.data;

import com.ple.jerbil.data.query.CompleteQuery;
import com.ple.jerbil.data.query.CreateQuery;
import com.ple.jerbil.data.query.QueryList;
import com.ple.jerbil.data.query.Table;
import com.ple.jerbil.data.selectExpression.Column;
import com.ple.util.IArrayList;
import com.ple.util.IList;
import io.r2dbc.spi.Result;
import org.jetbrains.annotations.Nullable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

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

  public Database(String name, @Nullable IList<Table> tables) {
    this.name = name;
    this.tables = tables;
  }

  public static Database make(String name) {
    return new Database(name, null);
  }

  public static Database make(String name, IList<Table> tables, String errorMessage, GeneratedType generatedType) {
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
    return sync(DdlOption.update);
  }

  public Mono<SyncResult> sync(DdlOption ddlOption) {
    if (ddlOption == DdlOption.create) {
      return SyncResult.make(this).createSchema(Create.shouldNotDrop)
        .flatMap(SyncResult::checkDbStructure)
        .flatMap(SyncResult::checkTableStructure);
    } else if (ddlOption == DdlOption.update) {
      return SyncResult.make(this).createSchema(Create.shouldNotDrop)
        .flatMap(SyncResult::updateSchemaStructure)
        .flatMap(SyncResult::updateTableStructure);
    } else if (ddlOption == DdlOption.replace) {
      return DataGlobal.bridge.execute("drop database if exists " + name)
        .then(SyncResult.make(this).createSchema(Create.shouldDrop));
//      return createSchema(Create.shouldDrop);
    } else if (ddlOption == DdlOption.replaceDrop) {
      return SyncResult.make(this).createSchema(Create.shouldDrop);
    }
    return Mono.just(SyncResult.make(this));
  }
}
