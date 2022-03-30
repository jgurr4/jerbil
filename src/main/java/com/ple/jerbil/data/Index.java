package com.ple.jerbil.data;

import com.ple.util.Immutable;
import com.ple.jerbil.data.query.Table;
import com.ple.jerbil.data.selectExpression.Column;
import com.ple.util.*;

import java.util.ArrayList;
import java.util.Objects;

@Immutable
public class Index {
  public final IndexType type;
  public final String indexName;
  public final Table table;
  public final IMap<String, IndexedColumn> indexedColumns;
//  @Nullable public final FkReference fkReference;

  protected Index(IndexType type, String indexName, Table table, IMap<String, IndexedColumn> indexedColumns) {
    this.type = type;
    this.indexName = indexName;
    this.table = table;
    this.indexedColumns = indexedColumns;
  }

  public static Index make(IndexType indexType, String indexName, Table table, IndexedColumn... indexedColumns) {
    IMap<String, IndexedColumn> indexedColumnsMap = IArrayMap.empty;
    for (IndexedColumn indexedColumn : indexedColumns) {
      indexedColumnsMap = indexedColumnsMap.put(indexedColumn.columnName, indexedColumn);
    }
    return new Index(indexType, indexName, table, indexedColumnsMap);
  }

  public static Index make(IndexType indexType, String indexName, Table table, Column... columns) {
    IMap<String, IndexedColumn> indexedColumnsMap = IArrayMap.empty;
    for (Column column : columns) {
      indexedColumnsMap = indexedColumnsMap.put(column.columnName, IndexedColumn.make(column.columnName, 0, null));
    }
    return new Index(indexType, indexName, table, indexedColumnsMap);
  }

/* // Deprecated
  public static Index make(IndexType indexType, IList<String> existingIdxNames, Table table, IndexedColumn... indexedColumns) {
    if (indexType.equals(IndexType.primary)) {
      return Index.make(indexType, "primary", table, indexedColumns);
    }
    Column[] columns = new Column[indexedColumns.length];
    for (int i = 0; i < indexedColumns.length; i++) {
      columns[i] = indexedColumns[i].column;
    }
    return Index.make(indexType, DatabaseService.generateIndexName(existingIdxNames, columns),
        table, indexedColumns);
  }
*/

  public static Index make(IndexType indexType, IList<String> existingIdxNames, Table table, Column column) {
    final ArrayList<String> list = new ArrayList();
    list.toArray(new String[0]);
    return null;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Index)) return false;
    Index index = (Index) o;
    return type == index.type && indexName.equals(index.indexName) && table.equals(
        index.table) && indexedColumns.equals(
        index.indexedColumns);
  }

  @Override
  public int hashCode() {
    return Objects.hash(type, indexName, table, indexedColumns);
  }

  @Override
  public String toString() {
    return "Index{" +
        "type=" + type +
        ", indexName='" + indexName + '\'' +
        ", table=" + table +
        ", indexedColumns=" + indexedColumns +
        '}';
  }

/*
  public AlterQuery create() {
    return DataGlobal.bridge.getGenerator().create(this);
  }

  public AlterQuery drop() {
    return DataGlobal.bridge.getGenerator().drop(this);
  }
*/
}
