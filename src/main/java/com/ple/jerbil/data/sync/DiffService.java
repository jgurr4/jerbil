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
//          .map(diffWrap -> new DiffWrap(diffWrap.rightDb, diffWrap.diffs.combineDiffs(compareColumnProps(getColumns(leftDb.tables), getColumns(diffWrap.rightDb.tables)))))
          .map(diffWrap -> diffWrap.diffs);
      }));
    /*
     Returned object should be DbDiff containing 3 HashMaps of diffs based on create, delete, update.
     DbProps.tables is not a list of tablenames, or of tables, but rather a list of TableProp diffs.
     Similarly TableProps.columns is not a list of columns, but rather a list of ColumnProp diffs. This means combineDiffs is rather simple,
     It just fills the DbProps.tables value with list of tableDiffs if any exist, and it fills the TableProps.columns value with
     a list of ColumnDiffs if any exist.
    Example:
    DbDiff{
      create{ tables: [ missingTable{...}, user{columns: [missingColumn{...}]] }
      delete{ tables: [extraTable{...}, user{columns: [extraColumn{}]] }
      update{
      shouldBe{charset : utf8, tables : [ user{ columns : [ type{ enumValues : '"admin","customer","employee"' }}]]}
      is{charset : latin1, tables : [ user{columns : [ type{ enumValues : '"admin","customer"' }}]]}
      }
    }
    So far the only downside to this method above is it may be a little tricky to read because of how verbose it may be
    in the toString method for some people. Although in most cases there should be very few diffs, so it won't be an issue in most cases.
    Alternative name for shouldBe{} and is{}: left-side{} right-side{}.
     */
  }

  private static DbDiff compareDatabaseProps(Database leftDb, Database rightDb) {
    return null;
  }

  private static TableDiff compareTableProps(IList<Table> leftTables, IList<Table> rightTables) {
    TableDiff.combineDiffs(getColumns(leftTables), getColumns(rightTables));
    return null;
  }

  private static ColumnDiff compareColumnProps(IList<Column> leftColumns, IList<Column> rightColumns) {
    return null;
  }

  private static IList<Column> getColumns(IList<Table> tables) {
    return null;
  }

}
