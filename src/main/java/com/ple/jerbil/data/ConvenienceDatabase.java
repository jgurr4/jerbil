package com.ple.jerbil.data;

import com.ple.jerbil.data.query.TableContainer;
import com.ple.util.IList;

public class ConvenienceDatabase<T> extends DatabaseContainer {

  public final T tables;

  protected ConvenienceDatabase(Database database, IList<TableContainer> tableContainers, T tables) {
    super(database, tableContainers);
    this.tables = tables;
  }

  public static <T> ConvenienceDatabase make(Database database, IList<TableContainer> tableContainers, T tables) {
    return new ConvenienceDatabase(database, tableContainers, tables);
  }

}
