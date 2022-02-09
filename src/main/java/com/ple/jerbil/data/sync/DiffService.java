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
      .map(e -> compareTableLists(leftDb.tables, rightDb.tables));

//  return ReactorMono.make(Mono.just(DbDiff.make(nameDiff.toFuture().get(), tablesDiff.toFuture().get())));   //Alternative method. Requires exception handling.
    return ReactorMono.make(Mono.from(nameDiff)
      .flatMap(nDiff -> Mono.from(tablesDiff)
        .map(tDiff -> DbDiff.make(nDiff, tDiff))
      )
    );
  }

  private static class TableNameMatchWrapper {
    public final Table table;
    public final boolean nameMatched;
    protected TableNameMatchWrapper(Table table, boolean nameMatched) {
      this.table = table;
      this.nameMatched = nameMatched;
    }
    public static TableNameMatchWrapper make(Table table, boolean tableNameInList) {
      return new TableNameMatchWrapper(table, tableNameInList);
    }
  }

  public static VectorDiff<Table> compareTableLists(IList<Table> leftTables, IList<Table> rightTables) {
    IList<Table> create = null;
    IList<Table> delete = null;
    IList<Diff<Table>> update = null;
    //Step 1: filter out all the leftTables that are contained inside rightTables as well as any names which match. Remaining tables are missing and need to be created.
    final Flux<Table> missingTables = getMissingTables(leftTables, rightTables);
    //Step 2: Set create equal to the remaining tables after filtering in the form of an IArrayList.
//    create = missingTables.collectList().map(mTables -> IArrayList.make(mTables.toArray(Table.emptyArray)));  //FIXME: Find out why this doesn't work.
    try {
      create = IArrayList.make(missingTables.collectList().toFuture().get().toArray(Table.emptyArray));
    } catch (InterruptedException | ExecutionException e) {
      e.printStackTrace();
    }
    //Step 3: filter out all the righttables that are contained inside leftTables. These are matches and don't require creating or updating/deleting.
    final Flux<Table> noExactMatchesList = filterOutMatchingTables(leftTables, rightTables);
    //Step 4: Remaining rightTables must be compared to see if their names match any leftTables.
    final Flux<TableNameMatchWrapper> nameMatchOrNotList = noExactMatchesList.map(
      rTable -> TableNameMatchWrapper.make(rTable, CheckTableNameInList(leftTables, rTable)));
    // Step 5: Any rightTables which matched are the ones that contain diffs and must be deeply compared and diffs placed
    // inside tableDiff which is part of the update List<Diff<Table>>
    try {
      update = getListOfTableDiffs(leftTables, ReactorFlux.make(nameMatchOrNotList));
      // Step 6: Any rightTables which didn't match are considered an extra table that goes into the "delete Table" list.
      delete = getExtraTables(leftTables, ReactorFlux.make(nameMatchOrNotList));
    } catch (InterruptedException | ExecutionException e) {
      e.printStackTrace();
    }
    // Step 7: Return the completed VectorDiff which is made from the create, delete, and update lists.
    return new VectorDiff<>(create, delete, update);
  }

  public static IList<Table> getExtraTables(IList<Table> leftTables, ReactiveWrapper<TableNameMatchWrapper> nameMatchOrNotList)
    throws ExecutionException, InterruptedException {
    return nameMatchOrNotList.unwrapFlux().filter(nameMatchWrapper -> !nameMatchWrapper.nameMatched)
      .map(nameMatchWrapper -> nameMatchWrapper.table)
      .collectList()
      .map(exTables -> IArrayList.make(exTables.toArray(Table.emptyArray)))
      .defaultIfEmpty(null)
      .toFuture()
      .get();
  }

  public static IList<Diff<Table>> getListOfTableDiffs(IList<Table> leftTables,
                                                        ReactiveWrapper<TableNameMatchWrapper> nameMatchOrNotList
  ) throws ExecutionException, InterruptedException {
    return nameMatchOrNotList.unwrapFlux().map(nameMatchWrapper -> {
        if (nameMatchWrapper.nameMatched) {
          try {
            return compareTables(
              nameMatchWrapper.table,
              getTableMatchingName(nameMatchWrapper.table.name, leftTables).unwrapMono().toFuture().get()
            );
          } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
          }
        }
        return null;
      })
      .filter(Objects::nonNull)
      .collectList()
      .map(tableDiffs -> IArrayList.make(tableDiffs.toArray(TableDiff.empty)))
      .defaultIfEmpty(null)
      .toFuture()
      .get();
  }

  public static Flux<Table> getMissingTables(IList<Table> leftTables, IList<Table> rightTables) {
    return Flux.fromArray(leftTables.toArray())
      .filter(rightTables::contains)
      .filter(lTable -> !CheckTableNameInList(rightTables, lTable));
  }

  public static Flux<Table> filterOutMatchingTables(IList<Table> leftTables, IList<Table> rightTables) {
    return Flux.fromArray(rightTables.toArray())
      .filter(leftTables::contains);
  }

  public static Diff<Table> compareTables(Table t1, Table t2) {
    ScalarDiff<String> nameDiff = t1.name.equals(t2.name) ? null : ScalarDiff.make(t1.name, t2.name);
    VectorDiff<Column> columnsDiff = compareColumnLists(t1.columns, t2.columns);
    ScalarDiff<StorageEngine> storageEngineDiff = t1.engine.name().equals(t2.engine.name()) ? null : ScalarDiff.make(
      t1.engine, t2.engine);
    return TableDiff.make(nameDiff, columnsDiff, storageEngineDiff);
  }

  public static VectorDiff<Column> compareColumnLists(IList<Column> columns, IList<Column> columns1) {
    // Should return a VectorDiff<Column> which contains create, delete and update diffs. If no diffs are found, should
    // return null.
    return null;
  }

  public static Diff<Column> compareColumns(Column c1, Column c2) {
    //Should return a ColumnDiff containing all the diffs of column.
    // If no diffs exist between columns then it should return null.
    return null;
  }

  public static ReactiveWrapper<Table> getTableMatchingName(String name, IList<Table> tables) {
    return ReactorMono.make(Flux.fromArray(tables.toArray())
      .filter(table -> table.name.equals(name))
      .next());
  }

  public static boolean CheckTableNameInList(IList<Table> tables, Table t1) {
    for (Table table : tables) {
      if (table.name.equals(t1.name)) {
        return true;
      }
    }
    return false;
  }

}
