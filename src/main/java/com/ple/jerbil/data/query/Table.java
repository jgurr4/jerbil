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

  //FIXME: name and engine need to be updated to "TableName" and "StorageEngine" to avoid conflicts with column names.
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

  public QueryWithFrom where(BooleanExpression condition) {
    return QueryWithFrom.make(this).where(condition);
  }

  public QueryWithFrom join(FromExpression... tables) {
    FromExpression result = this;
    for (FromExpression table : tables) {
      result = Join.make(result, table);
    }
    return QueryWithFrom.make(result);
  }

  public CreateQuery create() {
    return CreateQuery.make(this);
  }

  public Table set(Column column) {
    return null;
  }

  public Table remove(Column column) {
    return null;
  }

  public PartialInsertQuery insert() {
    return PartialInsertQuery.make(this);
  }

  public CompleteQuery select(CountAgg agg) {
    return SelectQuery.make(this, IArrayList.make(agg));
  }

  public SelectQuery select(AliasedExpression... aliasedExpressions) {
    return SelectQuery.make(this, IArrayList.make(aliasedExpressions));
  }

  @Override
  protected void diffJoin() {
  }

  @Override
  public IList<Table> tableList() {
    return IArrayList.make(this);
  }

  public void add(Column... columnArr) {
    for (Column column : columnArr) {
      columns = columns.add(column);
    }
  }

  public void add(Column column) {
    final IList<Column> newColumns = columns.add(column);
    columns = newColumns;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Table)) return false;
    Table table = (Table) o;
    return storageEngine == table.storageEngine && tableName.equals(table.tableName) && columns.equals(table.columns);
  }

  @Override
  public int hashCode() {
    return Objects.hash(storageEngine, tableName, columns);
  }

  @Override
  public String toString() {
    return "Table{" +
      "name='" + tableName + '\'' +
      ", columns=" + columns +
      ", engine=" + storageEngine +
      '}';
  }

  public Table getTable() {
    return this;
  }

  public SyncResult sync() {
    return null;
  }
}
