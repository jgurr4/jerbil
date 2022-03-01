package com.ple.jerbil.data.sync;

import com.ple.jerbil.data.Index;
import com.ple.jerbil.data.IndexType;
import com.ple.jerbil.data.Order;
import com.ple.jerbil.data.selectExpression.Column;
import org.jetbrains.annotations.Nullable;

public class IndexDiff implements Diff<Index> {

  public static Diff<Index>[] empty = new IndexDiff[0];
  @Nullable public final ScalarDiff<IndexType> type;
  @Nullable public final ScalarDiff<String> indexName;
  @Nullable public final VectorDiff<Column> columns;
  @Nullable public final ScalarDiff<Integer> size;
  @Nullable public final ScalarDiff<Order> order;


  protected IndexDiff(@Nullable ScalarDiff<IndexType> type, @Nullable ScalarDiff<String> indexName,
                      @Nullable VectorDiff<Column> columns, @Nullable ScalarDiff<Integer> size,
                      @Nullable ScalarDiff<Order> order) {
    this.type = type;
    this.indexName = indexName;
    this.columns = columns;
    this.size = size;
    this.order = order;
  }

  public static IndexDiff make(@Nullable ScalarDiff<IndexType> type, @Nullable ScalarDiff<String> indexName,
                               @Nullable VectorDiff<Column> columns, @Nullable ScalarDiff<Integer> size,
                               @Nullable ScalarDiff<Order> order) {
    return new IndexDiff(type, indexName, columns, size, order);
  }

  @Override
  public int getTotalDiffs() {
    return 0;
  }

}
