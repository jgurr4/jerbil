package com.ple.jerbil.data;

import com.ple.jerbil.data.query.TableContainer;
import com.ple.util.IList;

public class DatabaseContainer {
  public final Database database;
  public final IList<TableContainer> tables;

  protected DatabaseContainer(Database database, IList<TableContainer> tables) {
    this.database = database;
    this.tables = tables;
  }

  public static DatabaseContainer make(Database database, IList<TableContainer> tables) {
    return new DatabaseContainer(database, tables);
  }

}
