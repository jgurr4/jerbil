package com.ple.jerbil.data.query;

import com.ple.jerbil.data.*;
import com.ple.jerbil.data.selectExpression.*;
import com.ple.jerbil.data.selectExpression.NumericExpression.NumericColumn;
import com.ple.jerbil.data.selectExpression.booleanExpression.BooleanExpression;
import com.ple.jerbil.data.sync.SyncResult;
import com.ple.util.IArrayList;
import com.ple.util.IList;
import com.ple.util.IMap;
import reactor.util.annotation.Nullable;

public class TableContainer extends FromExpression {
  public final Table table;
  public final IMap<String, Column> columns;
  @Nullable public final StorageEngine storageEngine;
  @Nullable public final IList<Index> indexes;
  @Nullable public final NumericColumn autoIncrementColumn;

  protected TableContainer(Table table, IMap<String, Column> columns, @Nullable StorageEngine storageEngine,
                           @Nullable IList<Index> indexes, @Nullable NumericColumn autoIncrementColumn) {
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

  public DeleteQuery delete() {
    return null;
  }

  public PartialUpdateQuery update() {
    return null;
  }

  public PartialInsertQuery replace() {
    // replace is just a insert with ignore, or on duplicate key update.
    return null;
  }

  public CompleteQuery select(CountAgg agg) {
    return SelectQuery.make(this, IArrayList.make(agg));
  }

  public SelectQuery select(AliasedExpression... aliasedExpressions) {
    return SelectQuery.make(this, IArrayList.make(aliasedExpressions));
  }

  @Override
  protected void diffJoin() {}

  public SelectQuery select(SelectExpression... selectExpressions) {
    return SelectQuery.make(this, IArrayList.make(selectExpressions));
  }

  @Override
  public IList<TableContainer> tableList() {
    return IArrayList.make(this);
  }

  public SelectQuery select() {
    return SelectQuery.make(this, IArrayList.make(SelectExpression.selectAll));
  }

  public PotentialQuery explain() {
    return null;
  }

  public PotentialQuery analyze() {
    return null;
  }

  public SelectQuery selectDistinct() {
    return null;
  }

  public SelectQuery selectDistinct(AliasedExpression... aliasedExpressions) {
    return null;
  }

  public SelectQuery selectDistinct(SelectExpression... selectExpressions) {
    return SelectQuery.make(this, IArrayList.make(selectExpressions), QueryFlags.make(0b10000000));
  }

  public String toSql() {
    return CreateQuery.make(this).toSql();
  }

  public static TableContainer fromSql(String showCreateTable, Database db) {
    if (DataGlobal.bridge == null) {
      throw new NullPointerException("Global.sqlGenerator not set.");
    }
    return fromSql(DataGlobal.bridge.getGenerator(), showCreateTable, db);
  }

  public static TableContainer fromSql(LanguageGenerator generator, String showCreateTable, Database db) {
    return generator.fromSql(showCreateTable, db);
  }
}
