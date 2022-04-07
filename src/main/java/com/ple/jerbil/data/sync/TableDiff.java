package com.ple.jerbil.data.sync;

import com.ple.jerbil.data.DataGlobal;
import com.ple.jerbil.data.DatabaseContainer;
import com.ple.jerbil.data.query.Table;
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

  public String toSql() {
    if (tableB.equals(TableContainer.empty)) {
      return tableA.create().toSql();
    }
    if (columns != null || tableName != null) {
      return "use " + tableA.table.database.databaseName + ";\n" + DataGlobal.bridge.getGenerator().toSql(this);
    }
    return "";
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
    String tblName = "";
    String sEngine = "";
    String cols = "";
    String idxes = "";
    String colsBefore = "  ";
    String colsAfter = "  ";
    String idxesBefore = "  ";
    String idxesAfter = "  ";
    String separator = "";
    String columnDiffs = "";
    String indexDiffs = "";
    boolean diffColumns = false;
    boolean diffIndexes = false;
    for (String column : tableA.columns.keys()) {
      colsBefore += separator + column;
      separator = " ";
      if (tableB.columns.get(column) == null) diffColumns = true;
    }
    separator = "";
    for (String column : tableB.columns.keys()) {
      colsAfter += separator + column;
      separator = " ";
      if (tableA.columns.get(column) == null) diffColumns = true;
    }
    if (columns != null && diffColumns) {
      cols = "\n  columns= \n left: " + colsBefore  + "\n right: " + colsAfter;
    }
    separator = "";
    for (String index : tableA.indexes.keys()) {
      idxesBefore += separator + index;
      separator = " ";
      if (tableB.indexes.get(index) == null) diffIndexes = true;
    }
    separator = "";
    for (String index : tableB.indexes.keys()) {
      idxesAfter += separator + index;
      separator = " ";
      if (tableA.indexes.get(index) == null) diffIndexes = true;
    }
    if (columns != null && diffIndexes) {
      idxes = "\n  indexes= \n    left: " + idxesBefore  + "\n    right: " + idxesAfter;
    }
    if (tableName != null) {
      tblName = "\n  tableName= \n    left: " + tableName.before + "\n    right: " + tableName.after;
    }
    if (storageEngine != null) {
      sEngine = "\n  storageEngine= \n    left: " + storageEngine.before + "\n    right: " + storageEngine.after;
    }
    separator = "\n";
    if (columns != null) {
      for (ColumnDiff colDiff : columns.update) {
        columnDiffs += separator + colDiff;
      }
    }
    if (indexes != null) {
      if (indexes.update != null) {
        for (IndexDiff idxDiff : indexes.update) {
          indexDiffs += separator + idxDiff;
        }
      }
    }
    return "TableDiff{ leftName: " + tableA.table.tableName + "  rightName: " + tableB.table.tableName +
        tblName +
        cols +
        idxes +
        sEngine +
        columnDiffs +
        indexDiffs +
        '}';
  }
}