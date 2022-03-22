package com.ple.jerbil.data.sync;

import com.ple.jerbil.data.*;
import com.ple.jerbil.data.query.TableContainer;
import com.ple.util.Nullable;

import java.util.Objects;

public class DbDiff implements Diff<Database> {

  @Nullable public final ScalarDiff<String> databaseName;
  @Nullable public final VectorDiff<TableContainer, TableDiff> tables;
  public final DatabaseContainer databaseA;
  public final DatabaseContainer databaseB;

//  public final VectorDiff<ViewTable> views;
//  public final ScalarDiff<CharSet> charSet;
//  public final VectorDiff<StoredProcedure> procedures;
//  public final VectorDiff<StoredFunction> functions;
//  public final VectorDiff<StoredEvent> events;
//  public final VectorDiff<StoredTrigger> triggers;

  protected DbDiff(ScalarDiff<String> databaseName, VectorDiff<TableContainer, TableDiff> tables,
                   DatabaseContainer databaseA, DatabaseContainer databaseB) {
    this.databaseName = databaseName;
    this.tables = tables;
    this.databaseA = databaseA;
//    this.views = views;
//    this.charSet = charSet;
//    this.procedures = procedures;
//    this.functions = functions;
//    this.events = events;
//    this.triggers = triggers;
    this.databaseB = databaseB;
  }

  public static DbDiff make(ScalarDiff<String> name, VectorDiff<TableContainer, TableDiff> tables, DatabaseContainer databaseA,
                            DatabaseContainer databaseB) {
    return new DbDiff(name, tables, databaseA, databaseB);
  }

  /**
   * This generates sql commands required to remove diffs from the right side Database so they more closely match the left side.
   * For example, if the create diff has a table inside it, that will cause this method to generate a create table statement for that table.
   * @return String
   */
  public String toSql() {
    if (databaseB.equals(DatabaseContainer.empty)) {
      return databaseA.createAll().toSql();
    }
    if (tables != null || databaseName != null) {
      return DataGlobal.bridge.getGenerator().toSql(this);
    }
    return null;
  }

  public DbDiff filter(DdlOption ddlOption) {
    VectorDiff<TableContainer, TableDiff> newTables = null;
    ScalarDiff<String> newDatabaseName = null;
    if (tables != null) {
       newTables = tables.filter(ddlOption);
    }
    if (databaseName != null) {
      newDatabaseName = databaseName.filter(ddlOption);
    }
    return new DbDiff(newDatabaseName, newTables, databaseA, databaseB);
  }

  @Override
  public int getTotalDiffs() {
    //TODO: Implement this.
    return 0;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof DbDiff)) return false;
    DbDiff dbDiff = (DbDiff) o;
    return Objects.equals(databaseName, dbDiff.databaseName) && Objects.equals(tables,
        dbDiff.tables) && databaseA.equals(dbDiff.databaseA) && databaseB.equals(dbDiff.databaseB);
  }

  @Override
  public int hashCode() {
    return Objects.hash(databaseName, tables, databaseA, databaseB);
  }

  @Override
  public String toString() {
    String dbName = "";
    String tbl = "";
    String tablesBefore = "";
    String tablesAfter = "";
    String separator = "";
    String tblDiffs = "";
    boolean diffTables = false;
    for (String table : databaseA.tables.keys()) {
      tablesBefore += separator + table;
      separator = " ";
      if (databaseB.tables.get(table) == null) diffTables = true;
    }
    separator = "";
    for (String table : databaseB.tables.keys()) {
      tablesAfter += separator + table;
      separator = " ";
      if (databaseA.tables.get(table) == null) diffTables = true;
    }
    if (databaseName != null) {
      dbName = "\n  databaseName= \n  left: " + databaseName.before + "\n  right: " + databaseName.after;
    }
    if (tables != null && diffTables) {
      tbl = "\n  tables= \n left: " + tablesBefore  + "\n right: " + tablesAfter;
    }
    separator = "\n";
    if (tables != null) {
      for (TableDiff tableDiff : tables.update) {
        tblDiffs += separator + tableDiff;
      }
    }
    return "DbDiff{ leftName: " + databaseA.database.databaseName + "  rightName: " + databaseB.database.databaseName +
        dbName +
        tbl + "\n" +
//        ", databaseA=" + databaseA +
//        ", databaseB=" + databaseB +
        tblDiffs +
        "\n}";
  }
}
