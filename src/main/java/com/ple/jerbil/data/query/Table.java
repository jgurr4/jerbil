package com.ple.jerbil.data.query;

import com.ple.jerbil.data.*;
import com.ple.jerbil.data.selectExpression.AliasedExpression;
import com.ple.jerbil.data.selectExpression.Column;
import com.ple.jerbil.data.selectExpression.CountAgg;
import com.ple.jerbil.data.selectExpression.booleanExpression.BooleanExpression;
import com.ple.jerbil.data.sync.SyncResult;
import com.ple.util.IArrayList;
import com.ple.util.IList;

import java.util.Objects;


/**
 * Table is a database Object which contains columns.
 */
@Immutable
public class Table extends FromExpression {

  public final String tableName;
  public final Database database;
  public final StorageEngine storageEngine;
  public static Table[] emptyArray = new Table[0];

  protected Table(String tableName, Database database) {
    this(tableName, database, StorageEngine.simple);
  }

  protected Table(String tableName, Database database, StorageEngine storageEngine) {
    this.tableName = tableName;
    this.database = database;
    this.storageEngine = storageEngine;
  }

  public static Table make(String name, Database database) {
    return new Table(name, database, StorageEngine.simple);
  }

  public static Table make(String name, Database database, StorageEngine engine) {
    return new Table(name, database, engine);
  }

  public String toSql() {
    return CreateQuery.make(this).toSql();
  }

  public static Table fromSql(String showCreateTable, Database db) {
    if (DataGlobal.bridge == null) {
      throw new NullPointerException("Global.sqlGenerator not set.");
    }
    return fromSql(DataGlobal.bridge.getGenerator(), showCreateTable, db);
  }

  public static Table fromSql(LanguageGenerator generator, String showCreateTable, Database db) {
    return generator.fromSql(showCreateTable, db);
  }

  @Override
  protected void diffJoin() {
  }

  @Override
  public IList<Table> tableList() {
    return null;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Table)) return false;
    Table table = (Table) o;
    return tableName.equals(table.tableName) && database.equals(table.database) && storageEngine == table.storageEngine;
  }

  @Override
  public int hashCode() {
    return Objects.hash(tableName, database, storageEngine);
  }

  @Override
  public String toString() {
    return "Table{" +
      "name='" + tableName + '\'' +
        "database='" + database + '\'' +
      ", engine=" + storageEngine +
      '}';
  }

}
