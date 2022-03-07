package com.ple.jerbil.data.sync;

import com.ple.jerbil.data.*;
import com.ple.jerbil.data.query.TableContainer;
import com.ple.jerbil.data.selectExpression.Column;
import com.ple.util.IList;

import java.util.List;

public class DbDiff implements Diff<Database> {

  public final ScalarDiff<String> databaseName;
  public final VectorDiff<TableContainer, TableDiff> tables;
//  public final VectorDiff<ViewTable> views;
//  public final ScalarDiff<CharSet> charSet;
//  public final VectorDiff<StoredProcedure> procedures;
//  public final VectorDiff<StoredFunction> functions;
//  public final VectorDiff<StoredEvent> events;
//  public final VectorDiff<StoredTrigger> triggers;

  protected DbDiff(ScalarDiff<String> databaseName, VectorDiff<TableContainer, TableDiff> tables) {
    this.databaseName = databaseName;
    this.tables = tables;
//    this.views = views;
//    this.charSet = charSet;
//    this.procedures = procedures;
//    this.functions = functions;
//    this.events = events;
//    this.triggers = triggers;
  }

  public static DbDiff make(ScalarDiff<String> name, VectorDiff<TableContainer, TableDiff> tables) {
    return new DbDiff(name, tables);
  }

  /**
   * This generates sql commands required to remove diffs from the right side Database so they more closely match the left side.
   * For example, if the create diff has a table inside it, that will cause this method to generate a create table statement for that table.
   * @return String
   */
  public String toSql() {
    if (this.tables != null || this.databaseName != null) {
      return DataGlobal.bridge.getGenerator().toSql(this);
    }
    return null;
  }

  public DbDiff filter(DdlOption ddlOption) {
    final VectorDiff<TableContainer, TableDiff> newTables = tables.filter(ddlOption);
    ScalarDiff<String> nameDiff = databaseName.filter(ddlOption);
    return DbDiff.make(nameDiff, newTables);
  }

  @Override
  public int getTotalDiffs() {
    //TODO: Implement this.
    return 0;
  }

}
