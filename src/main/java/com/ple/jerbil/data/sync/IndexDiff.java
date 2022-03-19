package com.ple.jerbil.data.sync;

import com.ple.jerbil.data.Index;
import com.ple.jerbil.data.IndexType;
import com.ple.jerbil.data.IndexedColumn;
import com.ple.jerbil.data.query.Table;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class IndexDiff implements Diff<Index> {

  public static Diff<Index>[] empty = new IndexDiff[0];
  @Nullable public final ScalarDiff<IndexType> type;
  @Nullable public final ScalarDiff<String> indexName;
  @Nullable public final ScalarDiff<Table> table;
  @Nullable public final VectorDiff<IndexedColumn, IndexedColumnDiff> indexedColumns;
  public final Index indexA;
  public final Index indexB;

  protected IndexDiff(@Nullable ScalarDiff<IndexType> type, @Nullable ScalarDiff<String> indexName,
                      @Nullable ScalarDiff<Table> table,
                      @Nullable VectorDiff<IndexedColumn, IndexedColumnDiff> indexedColumns,
                      Index indexA, Index indexB) {
    this.type = type;
    this.indexName = indexName;
    this.table = table;
    this.indexedColumns = indexedColumns;
    this.indexA = indexA;
    this.indexB = indexB;
  }

  public static IndexDiff make(@Nullable ScalarDiff<IndexType> type, @Nullable ScalarDiff<String> indexName,
                               @Nullable ScalarDiff<Table> table, @Nullable VectorDiff<IndexedColumn, IndexedColumnDiff> columns,
                               Index indexA, Index indexB) {
    return new IndexDiff(type, indexName, table, columns, indexA, indexB);
  }

  @Override
  public int getTotalDiffs() {
    return 0;
  }

  @Override
  public IndexDiff filter(DdlOption ddlOption) {
    ScalarDiff<IndexType> newType = null;
    VectorDiff<IndexedColumn, IndexedColumnDiff> newIndexedColumns = null;
    ScalarDiff<String> newIndexName = null;
    ScalarDiff<Table> newTable = null;
    if (type != null) {
      newType = type.filter(ddlOption);
    }
    if (indexName != null) {
      newIndexName = indexName.filter(ddlOption);
    }
    if (table != null) {
      newTable = table.filter(ddlOption);
    }
     newIndexedColumns = indexedColumns.filter(ddlOption);
    return new IndexDiff(newType, newIndexName, newTable, newIndexedColumns, indexA, indexB);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof IndexDiff)) return false;
    IndexDiff indexDiff = (IndexDiff) o;
    return Objects.equals(type, indexDiff.type) && Objects.equals(indexName,
        indexDiff.indexName) && Objects.equals(table, indexDiff.table) && Objects.equals(
        indexedColumns, indexDiff.indexedColumns) && indexA.equals(indexDiff.indexA) && indexB.equals(indexDiff.indexB);
  }

  @Override
  public int hashCode() {
    return Objects.hash(type, indexName, table, indexedColumns, indexA, indexB);
  }

  @Override
  public String toString() {
    return "IndexDiff{" +
        "type=" + type +
        ", indexName=" + indexName +
        ", table=" + table +
        ", indexedColumns=" + indexedColumns +
        ", indexA=" + indexA +
        ", indexB=" + indexB +
        '}';
  }
}
