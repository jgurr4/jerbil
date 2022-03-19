package com.ple.jerbil.data.sync;

import com.ple.util.Immutable;
import com.ple.jerbil.data.Index;
import com.ple.jerbil.data.StorageEngine;
import com.ple.jerbil.data.query.TableContainer;
import com.ple.jerbil.data.selectExpression.Column;

import java.util.Objects;

@Immutable
public class TableDiff implements Diff<TableContainer> {

  public static Diff<TableContainer>[] empty = new TableDiff[0];
  public final ScalarDiff<String> tableName;
  public final VectorDiff<Column, ColumnDiff> columns;
  public final VectorDiff<Index, IndexDiff> indexes;
  public final ScalarDiff<StorageEngine> storageEngine;
  public final TableContainer tableA;
  public final TableContainer tableB;
//  public final ScalarDiff<Expression> constraint;
//  public final ScalarDiff<String> comment;

  protected TableDiff(ScalarDiff<String> tableName, VectorDiff<Column, ColumnDiff> columns,
                      VectorDiff<Index, IndexDiff> indexes, ScalarDiff<StorageEngine> storageEngine,
                      TableContainer tableA, TableContainer tableB) {
    this.tableName = tableName;
    this.columns = columns;
    this.indexes = indexes;
    this.storageEngine = storageEngine;
//    this.constraint = constraint;
//    this.comment = comment;
    this.tableA = tableA;
    this.tableB = tableB;
  }

  public static TableDiff make(ScalarDiff<String> name, VectorDiff<Column, ColumnDiff> columns,
                               VectorDiff<Index, IndexDiff> indexes, ScalarDiff<StorageEngine> storageEngine,
                               TableContainer tableA, TableContainer tableB) {
    return new TableDiff(name, columns, indexes, storageEngine, tableA, tableB);
  }

  @Override
  public int getTotalDiffs() {
    return 0;
  }

  @Override
  public TableDiff filter(DdlOption ddlOption) {
    VectorDiff<Column, ColumnDiff> newColumns = null;
    ScalarDiff<StorageEngine> newEngine = null;
    VectorDiff<Index, IndexDiff> newIndexes = null;
    ScalarDiff<String> newTableName = null;
    if (columns != null) {
      newColumns = columns.filter(ddlOption);
    }
    if (storageEngine != null) {
       newEngine = storageEngine.filter(ddlOption);
    }
    if (indexes != null) {
       newIndexes = indexes.filter(ddlOption);
    }
    if (tableName != null) {
       newTableName = tableName.filter(ddlOption);
    }
    return new TableDiff(newTableName, newColumns, newIndexes, newEngine, tableA, tableB);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof TableDiff)) return false;
    TableDiff tableDiff = (TableDiff) o;
    return Objects.equals(tableName, tableDiff.tableName) && Objects.equals(columns,
        tableDiff.columns) && Objects.equals(indexes, tableDiff.indexes) && Objects.equals(
        storageEngine, tableDiff.storageEngine) && tableA.equals(tableDiff.tableA) && tableB.equals(tableDiff.tableB);
  }

  @Override
  public int hashCode() {
    return Objects.hash(tableName, columns, indexes, storageEngine, tableA, tableB);
  }

  @Override
  public String toString() {
    return "TableDiff{" +
        "tableName=" + tableName +
        ", columns=" + columns +
        ", indexes=" + indexes +
        ", storageEngine=" + storageEngine +
        ", tableA=" + tableA +
        ", tableB=" + tableB +
        '}';
  }
}
