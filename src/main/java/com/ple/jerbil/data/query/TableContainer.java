package com.ple.jerbil.data.query;

import com.ple.jerbil.data.Index;
import com.ple.jerbil.data.StorageEngine;
import com.ple.jerbil.data.selectExpression.AliasedExpression;
import com.ple.jerbil.data.selectExpression.Column;
import com.ple.jerbil.data.selectExpression.CountAgg;
import com.ple.jerbil.data.selectExpression.NumericExpression.NumericColumn;
import com.ple.jerbil.data.selectExpression.SelectExpression;
import com.ple.jerbil.data.selectExpression.booleanExpression.BooleanExpression;
import com.ple.jerbil.data.sync.SyncResult;
import com.ple.util.IArrayList;
import com.ple.util.IList;
import com.ple.util.IMap;
import reactor.util.annotation.Nullable;

public class TableContainer {
  public final Table table;
  public final IMap<String, Column> columns;
  @Nullable public final StorageEngine storageEngine;
  @Nullable public final IList<Index> indexes;
  @Nullable public final NumericColumn autoIncrementColumn;

  protected TableContainer(Table table, IMap<String, Column> columns, @Nullable StorageEngine storageEngine,
                           @Nullable IList<Index> indexes,
                           @Nullable NumericColumn autoIncrementColumn) {
    this.table = table;
    this.columns = columns;
    this.storageEngine = storageEngine;
    this.indexes = indexes;
    this.autoIncrementColumn = autoIncrementColumn;
  }

  public static TableContainer make(Table table, IMap<String, Column> columns, StorageEngine storageEngine, IList<Index> indexSpecs, NumericColumn autoIncrementColumn) {
    return new TableContainer(table, columns, storageEngine, indexSpecs, autoIncrementColumn);
  }

  public static TableContainer make(Table table, IMap<String, Column> columns) {
    return new TableContainer(table, columns, null, null, null);
  }

  public TableContainer add(Column... columnArr) {
    IMap<String, Column> newColumns = columns;
    for (Column column : columnArr) {
      newColumns = newColumns.put(column.columnName, column);
    }
    return new TableContainer(table, newColumns, storageEngine, indexes, autoIncrementColumn);
  }

  public TableContainer add(Column column) {
    final IMap<String, Column> newColumns = columns.put(column.columnName, column);
    return new TableContainer(table, newColumns, storageEngine, indexes, autoIncrementColumn);
  }

  public SyncResult sync() {
    return null;
  }

  public QueryWithFrom where(BooleanExpression condition) {
    return QueryWithFrom.make(table).where(condition);
  }

  public QueryWithFrom join(FromExpression... tables) {
    FromExpression result = table;
    for (FromExpression table : tables) {
      result = Join.make(result, table);
    }
    return QueryWithFrom.make(result);
  }

  public CreateQuery create() {
    return CreateQuery.make(table);
  }

  public Table set(Column column) {
    return null;
  }

  public Table remove(Column column) {
    return null;
  }

  public PartialInsertQuery insert() {
    return PartialInsertQuery.make(table);
  }

  public CompleteQuery select(CountAgg agg) {
    return SelectQuery.make(table, IArrayList.make(agg));
  }

  public SelectQuery select(AliasedExpression... aliasedExpressions) {
    return SelectQuery.make(table, IArrayList.make(aliasedExpressions));
  }

  public SelectQuery select(SelectExpression... selectExpressions) {
    return SelectQuery.make(table, IArrayList.make(selectExpressions));
  }

}
