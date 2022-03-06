package com.ple.jerbil.data.sync;

import com.ple.jerbil.data.Index;
import com.ple.jerbil.data.IndexType;
import com.ple.jerbil.data.IndexedColumn;
import com.ple.util.IList;
import org.jetbrains.annotations.Nullable;

public class IndexDiff implements Diff<Index> {

  public static Diff<Index>[] empty = new IndexDiff[0];
  @Nullable public final ScalarDiff<IndexType> type;
  @Nullable public final ScalarDiff<String> indexName;
  @Nullable public final VectorDiff<IndexedColumn, IndexedColumnDiff> indexedColumns;

  protected IndexDiff(@Nullable ScalarDiff<IndexType> type, @Nullable ScalarDiff<String> indexName,
                      @Nullable VectorDiff<IndexedColumn, IndexedColumnDiff> indexedColumns) {
    this.type = type;
    this.indexName = indexName;
    this.indexedColumns = indexedColumns;
  }

  public static IndexDiff make(@Nullable ScalarDiff<IndexType> type, @Nullable ScalarDiff<String> indexName,
                               @Nullable VectorDiff<IndexedColumn, IndexedColumnDiff> columns) {
    return new IndexDiff(type, indexName, columns);
  }

  @Override
  public int getTotalDiffs() {
    return 0;
  }

}
