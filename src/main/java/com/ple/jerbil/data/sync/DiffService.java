package com.ple.jerbil.data.sync;

import com.ple.jerbil.data.Database;
import com.ple.jerbil.data.bridge.ReactiveWrapper;
import com.ple.jerbil.data.bridge.ReactorMono;
import com.ple.jerbil.data.query.Table;
import com.ple.jerbil.data.selectExpression.Column;
import com.ple.util.IList;

/**
 * Contains static methods for obtaining all differences between existing database structure and Database Object.
 * Overall checks for extra, missing or conflicting tables, columns, indexes and procedures of existing database and the
 * Database Object.
 */
public class DiffService {

  public static class DiffWrap {
    public final Database rightDb;
    public final DbDiff diffs;
    public DiffWrap(Database rightDb, DbDiff diffs ) {
      this.rightDb = rightDb;
      this.diffs = diffs;
    }
  }

  public static ReactiveWrapper<DbDiff> compare(ReactiveWrapper<Database> db1, ReactiveWrapper<Database> db2) {
    // This method must compare every part of database with the remote/local database and log the differences inside
    // each of the IHashMaps. Basically every property of the database needs to be compared, including the database name,
    // the list of tables, the list of columns inside each table and all the other properties associated with columns/tables.
    return ReactorMono.make(db1.unwrapMono()
      .flatMap(leftDb -> {
        return db2.unwrapMono()
          .map(rightDb -> new DiffWrap(rightDb, compareDatabaseProps(leftDb, rightDb)))
          .map(diffWrap -> new DiffWrap(diffWrap.rightDb, diffWrap.diffs.combineDiffs(compareTableProps(leftDb.tables, diffWrap.rightDb.tables))))
          .map(diffWrap -> new DiffWrap(diffWrap.rightDb, diffWrap.diffs.combineDiffs(compareColumnProps(getColumns(leftDb.tables), getColumns(diffWrap.rightDb.tables)))))
          .map(diffWrap -> diffWrap.diffs);
      }));
  }

  private static DbDiff compareDatabaseProps(Database leftDb, Database rightDb) {
    return null;
  }

  private static TableDiff compareTableProps(IList<Table> leftTables, IList<Table> rightTables) {
    return null;
  }

  private static ColumnDiff compareColumnProps(IList<Column> leftColumns, IList<Column> rightColumns) {
    return null;
  }

  private static IList<Column> getColumns(IList<Table> tables) {
    return null;
  }

}
