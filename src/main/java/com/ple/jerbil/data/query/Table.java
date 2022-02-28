package com.ple.jerbil.data.query;

import com.ple.jerbil.data.*;
import com.ple.jerbil.data.GenericInterfaces.Immutable;

import java.util.Objects;


/**
 * Table is a database Object which contains columns.
 */
@Immutable
public class Table {

  public final String tableName;
  public final Database database;
  public static Table[] emptyArray = new Table[0];

  protected Table(String tableName, Database database) {
    this.tableName = tableName;
    this.database = database;
  }

  public static Table make(String name, Database database) {
    return new Table(name, database);
  }


/*
  @Override
  protected void diffJoin() {
  }
*/
  //  @Override
//  public IList<Table> tableList() {
//    return null;
//  }
//
  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Table)) return false;
    Table table = (Table) o;
    return tableName.equals(table.tableName) && database.equals(table.database);
  }

  @Override
  public int hashCode() {
    return Objects.hash(tableName, database);
  }

  @Override
  public String toString() {
    return "Table{" +
        "tableName='" + tableName + '\'' +
        ", database=" + database +
        '}';
  }
}
