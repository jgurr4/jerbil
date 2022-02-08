package com.ple.jerbil.data.sync;

import com.ple.jerbil.data.Database;
import com.ple.jerbil.data.bridge.ReactiveWrapper;
import com.ple.jerbil.data.bridge.ReactorMono;
import com.ple.jerbil.data.query.Table;
import com.ple.jerbil.data.selectExpression.Column;
import com.ple.util.IArrayList;
import com.ple.util.IList;

/**
 * Contains static methods for obtaining all differences between existing database structure and Database Object.
 * Overall checks for extra, missing or conflicting tables, columns, indexes and procedures of existing database and the
 * Database Object.
 */
public class DiffService {

  public static class DiffWrap {
    public final Database rightDb;
    public final Diff diffs;
    public DiffWrap(Database rightDb, Diff diffs ) {
      this.rightDb = rightDb;
      this.diffs = diffs;
    }
  }

  public static ReactiveWrapper<DbDiff> compare(ReactiveWrapper<Database> db1, ReactiveWrapper<Database> db2) {
    // This method must compare every part of database with the remote/local database and log the differences inside
    // each of the IHashMaps. Basically every property of the database needs to be compared, including the database name,
    // the list of tables, the list of columns inside each table and all the other properties associated with columns/tables.
    return null;
//    return ReactorMono.make(db1.unwrapMono()
//      .flatMap(leftDb -> {
//        return db2.unwrapMono()
//          .map(rightDb -> new DiffWrap(rightDb, compareDatabaseProps(leftDb, rightDb)))
//          .map(diffWrap -> )
//      }));
  }

  private static DbDiff compareDatabaseProps(Database leftDb, Database rightDb) {
    // This is sample code just to explain the process. You must do similar to this in reactive streams api.
    //Check if name is the same. If not run this code:
    final ScalarDiff<String> nameDiff = ScalarDiff.make(leftDb.name, rightDb.name);
    //Check if tables are the same. If not run this code:
    final IList<Diff<Table>> tableDiffs = compareTableProps(leftDb.tables, rightDb.tables);
    final VectorDiff<Table> tablesDiff = VectorDiff.make(null, null, tableDiffs); //FIXME: figure out how to get make method to accept All types of diffs for update.
    final DbDiff dbDiff = DbDiff.make(nameDiff, tablesDiff);
    return dbDiff;
  }

  private static IList<Diff<Table>> compareTableProps(IList<Table> leftTables, IList<Table> rightTables) {
    //Step 1: filter out all the righttables that are contained inside leftTables.
    for (Table rightTable : rightTables) {
      leftTables.contains(rightTable);
    }
    //Step 2: Remaining rightTables must be compared to see if their names match any leftTables.
    // If name matches, search for diffs inside column
    // If name doesn't match, create a IList<Table> delete for extra Table. (Doesn't mean it will be deleted, only if user wants deletions).
    for (Table leftTable : leftTables) {
      for (Table rightTable : rightTables) {
        if (leftTable.name.equals(rightTable.name)) {
          VectorDiff<Column> columnDiffs = compareColumnProps(leftTable.columns, rightTable.columns);
        } else {
          VectorDiff<Table> tableDiffs = VectorDiff.make(null, IArrayList.make(rightTable), null);
        }
      }
    }
    return IArrayList.make(TableDiff.make(ScalarDiff.make("user", "use"), null, null));
  }

  private static VectorDiff<Column> compareColumnProps(IList<Column> leftColumns, IList<Column> rightColumns) {
    return null;
  }

  private static IList<Column> getColumns(IList<Table> tables) {
    return null;
  }

}
