package com.ple.jerbil.data;

import com.ple.jerbil.data.selectExpression.Column;
import com.ple.util.IArrayList;
import com.ple.util.IList;
import org.jetbrains.annotations.Nullable;

@Immutable
public class Index {
  public final IndexType type;
  @Nullable public final String indexName;
  public final IList<Column> columns;
  @Nullable public final int size;
  @Nullable public final Order order;

  protected Index(IndexType type, @Nullable String indexName, IList<Column> columns, @Nullable int size, @Nullable Order order) {
    this.type = type;
    this.indexName = indexName;
    this.columns = columns;
    this.size = size;
    this.order = order;
  }

  public static Index make(IndexType indexType, String indexName, int size, Order order, Column... columns) {
    return new Index(indexType, indexName, IArrayList.make(columns), size, order);
  }

  public static Index make(IndexType indexType, String indexName, Column... columns) {
    return new Index(indexType, indexName, IArrayList.make(columns), 0, null);
  }

  public static Index make(IndexType indexType, Column... columns) {
    return new Index(indexType, null, IArrayList.make(columns), 0, null);
  }

  public static Index make(IndexType indexType, int size, Order order, Column... columns) {
    return new Index(indexType, null, IArrayList.make(columns), size, order);
  }

}
