package com.ple.jerbil.data;

import com.ple.jerbil.data.selectExpression.Column;
import org.jetbrains.annotations.Nullable;

public class IndexedColumn {
  public static final IndexedColumn[] emptyArray = new IndexedColumn[0];
  public final Column column;
  @Nullable public final int prefixSize;
  @Nullable public final SortOrder sortOrder;

  protected IndexedColumn(Column column, @Nullable int prefixSize, @Nullable SortOrder sortOrder) {
    this.column = column;
    this.prefixSize = prefixSize;
    this.sortOrder = sortOrder;
  }

  public static IndexedColumn make(Column column, @Nullable int prefixSize, @Nullable SortOrder sortOrder) {
    return new IndexedColumn(column, prefixSize, sortOrder);
  }

}
