package com.ple.jerbil.data;

import com.ple.jerbil.data.GenericInterfaces.Immutable;
import com.ple.jerbil.data.query.Table;
import com.ple.jerbil.data.selectExpression.Column;
import com.ple.util.*;
import org.jetbrains.annotations.Nullable;

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
      indexedColumnsMap.put(indexedColumn.column.columnName, indexedColumn);
    }
    return new Index(indexType, indexName, table, indexedColumnsMap);
  }

  public static Index make(IndexType indexType, IList<String> existingIdxNames, Table table, IndexedColumn... indexedColumns) {
    if (indexType.equals(IndexType.primary)) {
      return Index.make(indexType, "primary", table, indexedColumns);
    }
    IList<Column> columns = IArrayList.empty;
    for (IndexedColumn indexedColumn : indexedColumns) {
      columns = columns.add(indexedColumn.column);
    }
    return Index.make(indexType, DatabaseService.generateIndexName(existingIdxNames, columns.toArray()),
        table, indexedColumns);
  }

}
