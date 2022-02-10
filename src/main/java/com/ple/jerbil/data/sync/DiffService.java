package com.ple.jerbil.data.sync;

import com.ple.jerbil.data.Database;
import com.ple.jerbil.data.StorageEngine;
import com.ple.jerbil.data.bridge.ReactiveWrapper;
import com.ple.jerbil.data.bridge.ReactorFlux;
import com.ple.jerbil.data.bridge.ReactorMono;
import com.ple.jerbil.data.query.Table;
import com.ple.jerbil.data.selectExpression.Column;
import com.ple.util.IArrayList;
import com.ple.util.IList;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Objects;
import java.util.concurrent.ExecutionException;

/**
 * Contains static methods for obtaining all differences between existing database structure and Database Object.
 * Overall checks for extra, missing or conflicting tables, columns, indexes and procedures of existing database and the
 * Database Object.
 */
public class DiffService {

  public static ReactiveWrapper<DbDiff> compareDatabases(ReactiveWrapper<Database> db1, ReactiveWrapper<Database> db2) {
    // This method must compare every part of database with the remote/local database and record the diffs inside DbDiff.
    return ReactorMono.make(Mono.from(db1.unwrapMono())
      .concatWith(db2.unwrapMono())
      .collectList()
      .flatMap(databases -> compareDatabaseProps(databases.get(0), databases.get(1)).unwrapMono()));
  }

  private static ReactiveWrapper<DbDiff> compareDatabaseProps(Database leftDb, Database rightDb) {
    final Mono<ScalarDiff<String>> nameDiff = Mono.just(0)
      .filter(e -> leftDb.name.equals(rightDb.name))
      .map(e -> ScalarDiff.make(leftDb.name, rightDb.name));  //Can be null or can be ScalarDiff of before and after.
    Mono<VectorDiff<Table>> tablesDiff = Mono.just(0)
      .map(e -> compareListOfTables(leftDb.tables, rightDb.tables));

//  return ReactorMono.make(Mono.just(DbDiff.make(nameDiff.toFuture().get(), tablesDiff.toFuture().get())));   //Alternative method. Requires exception handling.
    return ReactorMono.make(Mono.from(nameDiff)
      .flatMap(nDiff -> Mono.from(tablesDiff)
        .map(tDiff -> DbDiff.make(nDiff, tDiff))
      )
    );
  }

  public static VectorDiff<Table> compareListOfTables(IList<Table> leftTables, IList<Table> rightTables) {
    IList<Table> create = getMissingTables(leftTables, rightTables);
    final IList<Table> noExactMatchesList = filterOutMatchingTables(leftTables, rightTables);
    IList<Table> matches = IArrayList.make();
    IList<Table> nonMatches = IArrayList.make();
    for (Table table : noExactMatchesList) {
      if (checkTableNameInList(leftTables, table)) {
        matches = matches.add(table);
      } else {
        nonMatches = nonMatches.add(table);
      }
    }
    IList<Diff<Table>> update = getListOfTableDiffs(leftTables, matches);
    IList<Table> delete = getExtraTables(leftTables, nonMatches);
    return new VectorDiff<>(create, delete, update);
  }

  public static IList<Table> getMissingTables(IList<Table> leftTables, IList<Table> rightTables) {
    IList<Table> result = IArrayList.make();
    for (Table lTable : leftTables) {
      if (!rightTables.contains(lTable) && !checkTableNameInList(rightTables, lTable)) {
        result = result.add(lTable);
      }
    }
    if (result.length() == 0) {
      return null;
    }
    return result;
  }

  public static IList<Table> getExtraTables(IList<Table> leftTables, IList<Table> rightTables) {
    IList<Table> result = IArrayList.make();
    for (Table rTable : rightTables) {
      if (!leftTables.contains(rTable) && !checkTableNameInList(leftTables, rTable)) {
        result = result.add(rTable);
      }
    }
    if (result.length() == 0) {
      return null;
    }
    return result;
  }

  public static IList<Diff<Table>> getListOfTableDiffs(IList<Table> leftTables, IList<Table> rightTables) {
    //TODO: Ask if this is bad practice to use Streams or declarative style programming for accomplishing intermediate
    // operation in a synchronous/mostly imperative style method.
    return Flux.fromIterable(rightTables)
      .filter(Objects::nonNull)
      .map(table -> compareTables(getTableMatchingName(leftTables, table.name), table))
      .collectList()
      .map(tableDiffs -> IArrayList.make(tableDiffs.toArray(TableDiff.empty)))
      .defaultIfEmpty(null)
      .block();
  }

  public static IList<Table> filterOutMatchingTables(IList<Table> leftTables, IList<Table> rightTables) {
    IList<Table> nonMatchingTables = IArrayList.make();
    for (Table rightTable : rightTables) {
        if (!leftTables.contains(rightTable)) {
          nonMatchingTables = nonMatchingTables.add(rightTable);
        }
      }
    return nonMatchingTables;
  }

  public static Diff<Table> compareTables(Table t1, Table t2) {
    ScalarDiff<String> nameDiff = t1.name.equals(t2.name) ? null : ScalarDiff.make(t1.name, t2.name);
    VectorDiff<Column> columnsDiff = compareListOfColumns(t1.columns, t2.columns);
    ScalarDiff<StorageEngine> storageEngineDiff = t1.engine.name().equals(t2.engine.name()) ? null : ScalarDiff.make(
      t1.engine, t2.engine);
    return TableDiff.make(nameDiff, columnsDiff, storageEngineDiff);
  }

  public static Table getTableMatchingName(IList<Table> tables, String name) {
    Table result = null;
    for (Table t : tables) {
      if (t.name.equals(name)) {
        result = t;
      }
    }
    return result;
  }

  public static VectorDiff<Column> compareListOfColumns(IList<Column> leftColumns, IList<Column> rightColumns) {
    IList<Column> create = getMissingColumns(leftColumns, rightColumns);
    final IList<Column> noExactMatchesList = filterOutMatchingColumns(leftColumns, rightColumns);
    IList<Column> matches = IArrayList.make();
    IList<Column> nonMatches = IArrayList.make();
    for (Column column : noExactMatchesList) {
      if (checkColumnNameInList(leftColumns, column)) {
        matches = matches.add(column);
      } else {
        nonMatches = nonMatches.add(column);
      }
    }
    IList<Diff<Column>> update = getListOfColumnDiffs(leftColumns, matches);
    IList<Column> delete = getExtraColumns(leftColumns, nonMatches);
    return new VectorDiff<>(create, delete, update);
    //TODO: Decide if results should be empty or if object returned should be null.
  }

  private static IList<Diff<Column>> getListOfColumnDiffs(IList<Column> leftColumns, IList<Column> matches) {
    return null;
  }

  private static IList<Column> getExtraColumns(IList<Column> leftColumns, IList<Column> nonMatches) {
    return null;
  }

  private static boolean checkColumnNameInList(IList<Column> leftColumns, Column column) {
    return false;
  }

  private static IList<Column> filterOutMatchingColumns(IList<Column> leftColumns, IList<Column> rightColumns) {
    return null;
  }

  private static IList<Column> getMissingColumns(IList<Column> leftColumns, IList<Column> rightColumns) {
    return null;
  }

  public static Diff<Column> compareColumns(Column c1, Column c2) {
    //Should return a ColumnDiff containing all the diffs of column.
    // If no diffs exist between columns then it should return null.
    return null;
  }

  public static boolean checkTableNameInList(IList<Table> tables, Table t1) {
    for (Table table : tables) {
      if (table.name.equals(t1.name)) {
        return true;
      }
    }
    return false;
  }

/* Example of how to turn these methods into generic methods.
  public static <T> boolean checkTableNameInList(IList<T> objects, T t1) {
    if (t1 instanceof Table) {
      for (T object : objects) {
        if (((Table) object).name.equals(((Table)t1).name)) {
          return true;
        }
      }
    } else if (t1 instanceof Column) {
      for (T object : objects) {
        if (((Column) object).name.equals(((Column)t1).name)) {
          return true;
        }
      }
    }
    return false;
  }
*/

}
