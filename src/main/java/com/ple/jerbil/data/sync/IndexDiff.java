package com.ple.jerbil.data.sync;

import com.ple.jerbil.data.Index;
import com.ple.jerbil.data.IndexType;
import com.ple.jerbil.data.IndexedColumn;
import com.ple.jerbil.data.SortOrder;
import com.ple.jerbil.data.selectExpression.Column;
import com.ple.util.IList;
import org.jetbrains.annotations.Nullable;

//FIXME: Change VectorDiff<Column> to VectorDiff<IndexedColumns>
public class IndexDiff implements Diff<Index> {

  public static Diff<Index>[] empty = new IndexDiff[0];
  @Nullable public final ScalarDiff<IndexType> type;
  @Nullable public final ScalarDiff<String> indexName;
  @Nullable public final VectorDiff<Column> columns;
  @Nullable public final IList<ColumnDiff> columnDiffs;
  @Nullable public final ScalarDiff<Integer> size;
  @Nullable public final ScalarDiff<SortOrder> order;


  protected IndexDiff(@Nullable ScalarDiff<IndexType> type, @Nullable ScalarDiff<String> indexName,
                      @Nullable VectorDiff<Column> columns, @Nullable IList<ColumnDiff> columnDiffs,
                      @Nullable ScalarDiff<Integer> size, @Nullable ScalarDiff<SortOrder> order) {
    this.type = type;
    this.indexName = indexName;
    this.columns = columns;
    this.columnDiffs = columnDiffs;
    this.size = size;
    this.order = order;
  }

  public static IndexDiff make(@Nullable ScalarDiff<IndexType> type, @Nullable ScalarDiff<String> indexName,
                               @Nullable VectorDiff<Column> columns, @Nullable IList<ColumnDiff> columnDiffs,
                               @Nullable ScalarDiff<Integer> size, @Nullable ScalarDiff<SortOrder> order) {
    return new IndexDiff(type, indexName, columns, columnDiffs, size, order);
  }

  public static IndexDiff make(ScalarDiff<IndexType> type, ScalarDiff<String> indexName,
                               VectorDiff<IndexedColumn> indexedColumns, IList<ColumnDiff> columnDiffs) {
    //TODO: Implement this.
    return null;
  }

  @Override
  public int getTotalDiffs() {
    return 0;
  }

}
