package com.ple.jerbil.data.sync;

import com.ple.jerbil.data.Index;
import com.ple.jerbil.data.IndexType;
import com.ple.jerbil.data.IndexedColumn;
import com.ple.jerbil.data.query.Table;
import org.jetbrains.annotations.Nullable;

public class IndexDiff implements Diff<Index> {

  public static Diff<Index>[] empty = new IndexDiff[0];
  @Nullable public final ScalarDiff<IndexType> type;
  @Nullable public final ScalarDiff<String> indexName;
  @Nullable public final ScalarDiff<Table> table;
  @Nullable public final VectorDiff<IndexedColumn, IndexedColumnDiff> indexedColumns;

  protected IndexDiff(@Nullable ScalarDiff<IndexType> type, @Nullable ScalarDiff<String> indexName, @Nullable ScalarDiff<Table> table,
                      @Nullable VectorDiff<IndexedColumn, IndexedColumnDiff> indexedColumns) {
    this.type = type;
    this.indexName = indexName;
    this.table = table;
    this.indexedColumns = indexedColumns;
  }

  public static IndexDiff make(@Nullable ScalarDiff<IndexType> type, @Nullable ScalarDiff<String> indexName,
                               @Nullable ScalarDiff<Table> table, @Nullable VectorDiff<IndexedColumn, IndexedColumnDiff> columns) {
    return new IndexDiff(type, indexName, table, columns);
  }

  @Override
  public int getTotalDiffs() {
    return 0;
  }

}
