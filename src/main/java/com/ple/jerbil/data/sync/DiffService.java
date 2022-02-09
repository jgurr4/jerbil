package com.ple.jerbil.data.sync;

import com.ple.jerbil.data.Database;
import com.ple.jerbil.data.bridge.ReactiveWrapper;
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

  public static class DiffWrap {

    public final ScalarDiff<String> val1;
    public final VectorDiff<Table> val2;

    public DiffWrap(ScalarDiff<String> val1, VectorDiff<Table> val2) {
      this.val1 = val1;
      this.val2 = val2;
    }

  }

  public static ReactiveWrapper<DbDiff> compare(ReactiveWrapper<Database> db1, ReactiveWrapper<Database> db2) {
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

  private static VectorDiff<Table> compareTableLists(IList<Table> leftTables, IList<Table> rightTables) {
    IList<Table> create = null;
    IList<Table> delete = null;
    IList<Diff<Table>> update = null;

    //Step 1: filter out all the leftTables that are contained inside rightTables as well as any names which match. Remaining tables are missing and need to be created.
    final Flux<Table> missingTables = Flux.just(leftTables.toArray())
      .filter(rightTables::contains)
      .filter(lTable -> !CheckTableNameInList(rightTables, lTable));

//    create = missingTables.collectList().map(mTables -> IArrayList.make(mTables.toArray(new Table[0]))).cast(IList.class);
    //Step 2: Set create equal to the remaining tables after filtering in the form of an IArrayList.
    try {
      create = IArrayList.make(missingTables.collectList().toFuture().get().toArray(new Table[0]));
    } catch (InterruptedException | ExecutionException e) {
      e.printStackTrace();
    }

    //Step 3: filter out all the righttables that are contained inside leftTables. These are matches and don't require creating or updating/deleting.
    final Flux<Table> extraTables = Flux.just(rightTables.toArray())
      .filter(leftTables::contains);

    //Step 4: Remaining rightTables must be compared to see if their names match any leftTables.
    final Flux<TableNameMatchWrapper> trueAndFalseList = extraTables.map(rTable -> TableNameMatchWrapper.make(rTable, CheckTableNameInList(leftTables, rTable)));

    // Step 5: Any rightTables which matched are the ones that contain diffs and must be compared and diffs be placed inside tableDiff which is added to the update list
//    update = trueAndFalseList.map(nameMatchWrapper -> {
//      if (nameMatchWrapper.nameMatched) {
//        return CompareTables(nameMatchWrapper.table, getMatchingTable(nameMatchWrapper.table.name, leftTables));
//      }
//      return null;
//    })
//      .filter(Objects::nonNull)
//      .collectList()
//      .map(tableDiffs -> IArrayList.make(tableDiffs.toArray()))
//      .defaultIfEmpty(null);    //FIXME: Find out why Reactive Streams can never find out what type of object is being contained in IArrayList.

    // Step 6: Any rightTables which didn't match are considered an extra table that goes into the "delete Table" list.
//    delete = trueAndFalseList.filter(nameMatchWrapper -> !nameMatchWrapper.nameMatched)
//      .map(nameMatchWrapper -> nameMatchWrapper.table)
//      .collectList()
//      .map(exTables -> IArrayList.make(exTables.toArray()))
//      .defaultIfEmpty(null);    //FIXME: Find out why Reactive Streams can never find out what type of object is being contained in IArrayList.

    // Step 7: Return the completed VectorDiff which is made from the create, delete, and update lists.
    return new VectorDiff<>(create, delete, update);
  }

  private static VectorDiff<Table> CompareTables(Table t1, Table t2) {
    return null;
  }

  private static Table getMatchingTable(String name, IList<Table> tables) {
    //Use this method to retrieve a table from list which we know already matches the name.
    return null;
  }

  private static boolean CheckTableNameInList(IList<Table> tables, Table t1) {
    for (Table table : tables) {
      if (table.name.equals(t1.name)) {
        return true;
      }
    }
    return false;
  }

  private static VectorDiff<Column> compareColumnProps(IList<Column> leftColumns, IList<Column> rightColumns) {
    return null;
  }

  private static IList<Column> getColumns(IList<Table> tables) {
    return null;
  }

}
