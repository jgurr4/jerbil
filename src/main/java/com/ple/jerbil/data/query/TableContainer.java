package com.ple.jerbil.data.query;

import com.ple.jerbil.data.*;
import com.ple.jerbil.data.reactiveUtils.ReactiveMono;
import com.ple.jerbil.data.selectExpression.*;
import com.ple.jerbil.data.selectExpression.NumericExpression.NumericColumn;
import com.ple.jerbil.data.selectExpression.booleanExpression.BooleanExpression;
import com.ple.jerbil.data.sync.SyncResult;
import com.ple.jerbil.data.sync.TableDiff;
import com.ple.jerbil.data.translator.LanguageGenerator;
import com.ple.util.IArrayList;
import com.ple.util.IArrayMap;
import com.ple.util.IList;
import com.ple.util.IMap;
import reactor.util.annotation.Nullable;

import java.util.Objects;

public class TableContainer extends FromExpression {
  public static TableContainer empty = new TableContainer(Table.make("none", Database.make("none")), IArrayMap.empty, StorageEngine.simple, null, null);
  public final Table table;
  public final IMap<String, Column> columns;
  public final StorageEngine storageEngine;
  @Nullable public final IMap<String, Index> indexes;
  @Nullable public final NumericColumn autoIncrementColumn;

  protected TableContainer(Table table, IMap<String, Column> columns, StorageEngine storageEngine,
                           @Nullable IMap<String, Index> indexes, @Nullable NumericColumn autoIncrementColumn) {
    if (storageEngine == null) {
      storageEngine = StorageEngine.simple;
    }
    this.table = table;
    this.columns = columns;
    this.storageEngine = storageEngine;
    this.indexes = indexes;
    this.autoIncrementColumn = autoIncrementColumn;
  }

  public static TableContainer make(Table table, IMap<String, Column> columns, StorageEngine storageEngine,
                                    IMap<String, Index> indexSpecs, NumericColumn autoIncrementColumn) {
    return new TableContainer(table, columns, storageEngine, indexSpecs, autoIncrementColumn);
  }

  public static TableContainer make(Table table, IMap<String, Column> columns) {
    return new TableContainer(table, columns, StorageEngine.simple, null, null);
  }

  public TableContainer add(Index idx) {
    IMap<String, Index> newIndexes = indexes;
    newIndexes = newIndexes.put(idx.indexName, idx);
    return new TableContainer(table, columns, storageEngine, newIndexes, autoIncrementColumn);
}


  public TableContainer add(Column... columnArr) {
    IMap<String, Column> newColumns = columns;
    IMap<String, Index> newIndexes = indexes;
    IList<String> existingIdxNames = indexes.keys();
    NumericColumn autoIncCol = autoIncrementColumn;
    for (Column column : columnArr) {
      final String indexName = DatabaseService.generateIndexName(existingIdxNames, column);
      existingIdxNames = existingIdxNames.add(indexName);
      newColumns = newColumns.put(column.columnName, column);
      if (column.props.isAutoInc()) {
        autoIncCol = (NumericColumn) column;
        newIndexes = newIndexes.put(indexName, Index.make(IndexType.primary, indexName, column.table, column));
      }
    }
    return new TableContainer(table, newColumns, storageEngine, newIndexes, autoIncCol);
  }

  public ReactiveMono<SyncResult<TableDiff>> sync() {
    return null;
  }

  public ReactiveMono<SyncResult<TableDiff>> sync(TableContainer leftTable, TableContainer rightTable) {
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

  public TableContainer remove(Column... newColumns) {
    IMap<String, Column> nc = columns;
    for (Column column : newColumns) {
      nc = nc.remove(column.columnName);
    }
    return TableContainer.make(table, nc, storageEngine, indexes, autoIncrementColumn);
  }

  public PartialInsertQuery insert() {
    return PartialInsertQuery.make(this);
  }

  public DeleteQuery delete() {
    return DeleteQuery.make(null, this);
  }

  public PartialUpdateQuery update() {
    return PartialUpdateQuery.make(this);
  }

  public PartialInsertQuery replace() {
    return PartialInsertQuery.make(this, QueryFlags.make().replace());
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
    return QueryWithFrom.make(this, QueryFlags.make().explain());
  }

  public PotentialQuery analyze() {
    return QueryWithFrom.make(this, QueryFlags.make().analyze());
  }

  public SelectQuery selectDistinct() {
    return null;
  }

  public SelectQuery selectDistinct(AliasedExpression... aliasedExpressions) {
    return null;
  }

  public SelectQuery selectDistinct(SelectExpression... selectExpressions) {
    return SelectQuery.make(this, IArrayList.make(selectExpressions), QueryFlags.make().distinct());
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
    return generator.getTableFromSql(showCreateTable, db);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof TableContainer)) return false;
    TableContainer that = (TableContainer) o;
    return table.equals(that.table) && columns.equals(
        that.columns) && storageEngine == that.storageEngine && Objects.equals(indexes,
        that.indexes) && Objects.equals(autoIncrementColumn, that.autoIncrementColumn);
  }

  @Override
  public int hashCode() {
    return Objects.hash(table, columns, storageEngine, indexes, autoIncrementColumn);
  }

  @Override
  public String toString() {
    return "TableContainer{" +
        "table=" + table +
        ", columns=" + columns +
        ", storageEngine=" + storageEngine +
        ", indexes=" + indexes +
        ", autoIncrementColumn=" + autoIncrementColumn +
        '}';
  }

  public <T extends DbRecord> T load(int id) {
    return null;
  }

  public DbRecordId save(DbRecord record) {
    return null;
  }

  public CreateQuery create() {
    return CreateQuery.make(this);
  }

/*
  public AlterQuery drop() {
    return DataGlobal.bridge.getGenerator().drop(this);
  }
*/
}
