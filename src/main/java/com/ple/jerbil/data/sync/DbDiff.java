package com.ple.jerbil.data.sync;

import com.ple.jerbil.data.DataGlobal;
import com.ple.jerbil.data.Database;
import com.ple.jerbil.data.query.Table;

public class DbDiff implements Diff<Database> {

  public final ScalarDiff<String> name;
  public final VectorDiff<Table> tables;
//  public final VectorDiff<ViewTable> views;
//  public final ScalarDiff<CharSet> charSet;
//  public final VectorDiff<StoredProcedure> procedures;
//  public final VectorDiff<StoredFunction> functions;
//  public final VectorDiff<StoredEvent> events;
//  public final VectorDiff<StoredTrigger> triggers;

  protected DbDiff(ScalarDiff<String> name, VectorDiff<Table> tables) {
    this.name = name;
    this.tables = tables;
//    this.views = views;
//    this.charSet = charSet;
//    this.procedures = procedures;
//    this.functions = functions;
//    this.events = events;
//    this.triggers = triggers;
  }

  public static DbDiff make(ScalarDiff<String> name, VectorDiff<Table> tables) {
    return new DbDiff(name, tables);
  }

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
