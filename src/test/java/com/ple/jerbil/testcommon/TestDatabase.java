package com.ple.jerbil.testcommon;

import com.ple.jerbil.data.Database;
import com.ple.jerbil.data.DatabaseContainer;
import com.ple.jerbil.data.query.Table;
import com.ple.jerbil.data.query.TableContainer;
import com.ple.util.IList;

public class TestDatabase extends DatabaseContainer {

  public final TestDbTables tables;

  protected TestDatabase(Database database, IList<TableContainer> tableContainers, TestDbTables tables) {
    super(database, tableContainers);
    this.tables = tables;
  }

}
