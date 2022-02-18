package com.ple.jerbil.data;

import com.ple.jerbil.data.selectExpression.Column;
import com.ple.util.IArrayList;
import com.ple.util.IList;
import org.jetbrains.annotations.Nullable;

@Immutable
public class IndexSpec {
  public final IndexType indexType;
  @Nullable public final String indexName;
  public final IList<Column> columns;
  @Nullable public final int size;
  @Nullable public final IndexSort order;

  protected IndexSpec(IndexType indexType, @Nullable String indexName, IList<Column> columns, @Nullable int size, @Nullable IndexSort order) {
    this.indexType = indexType;
    this.indexName = indexName;
    this.columns = columns;
    this.size = size;
    this.order = order;
  }

  public static IndexSpec make(IndexType indexType, String indexName, int size, IndexSort order, Column... columns) {
    return new IndexSpec(indexType, indexName, IArrayList.make(columns), size, order);
  }

  public static IndexSpec make(IndexType indexType, String indexName, Column... columns) {
    return new IndexSpec(indexType, indexName, IArrayList.make(columns), 0, null);
  }

  public static IndexSpec make(IndexType indexType, Column... columns) {
    return new IndexSpec(indexType, null, IArrayList.make(columns), 0, null);
  }

  public static IndexSpec make(IndexType indexType, int size, IndexSort order, Column... columns) {
    return new IndexSpec(indexType, null, IArrayList.make(columns), size, order);
  }

}
