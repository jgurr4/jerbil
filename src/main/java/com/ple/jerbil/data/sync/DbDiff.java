package com.ple.jerbil.data.sync;

import com.ple.jerbil.data.DataGlobal;
import com.ple.jerbil.data.Database;
import com.ple.jerbil.data.query.Table;
import com.ple.jerbil.data.query.TableContainer;

public class DbDiff implements Diff<Database> {

  public final ScalarDiff<String> name;
  public final VectorDiff<TableContainer> tables;
//  public final VectorDiff<ViewTable> views;
//  public final ScalarDiff<CharSet> charSet;
//  public final VectorDiff<StoredProcedure> procedures;
//  public final VectorDiff<StoredFunction> functions;
//  public final VectorDiff<StoredEvent> events;
//  public final VectorDiff<StoredTrigger> triggers;

  protected DbDiff(ScalarDiff<String> name, VectorDiff<TableContainer> tables) {
    this.name = name;
    this.tables = tables;
//    this.views = views;
//    this.charSet = charSet;
//    this.procedures = procedures;
//    this.functions = functions;
//    this.events = events;
//    this.triggers = triggers;
  }

  public static DbDiff make(ScalarDiff<String> name, VectorDiff<TableContainer> tables) {
    return new DbDiff(name, tables);
  }

  /**
   * This generates sql commands required to remove diffs from the right side Database so they more closely match the left side.
   * For example, if the create diff has a table inside it, that will cause this method to generate a create table statement for that table.
   * @return String
   */
  public String toSql() {
    return DataGlobal.bridge.getGenerator().toSql(this);
  }

  public DbDiff filter(DdlOption ddlOption) {
    return null;
  }

  @Override
  public int getTotalDiffs() {
    return 1;
  }

}
