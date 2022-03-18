package com.ple.jerbil.data;

import com.ple.jerbil.data.selectExpression.Column;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class IndexedColumn {
  public static final IndexedColumn[] emptyArray = new IndexedColumn[0];
  public final Column column;
  public final int prefixSize;
  @Nullable public final SortOrder sortOrder;

  protected IndexedColumn(Column column, int prefixSize, @Nullable SortOrder sortOrder) {
    this.column = column;
    this.prefixSize = prefixSize;
    this.sortOrder = sortOrder;
  }

  public static IndexedColumn make(Column column, int prefixSize, @Nullable SortOrder sortOrder) {
    return new IndexedColumn(column, prefixSize, sortOrder);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof IndexedColumn)) return false;
    IndexedColumn that = (IndexedColumn) o;
    return prefixSize == that.prefixSize && column.equals(that.column) && sortOrder == that.sortOrder;
  }

  @Override
  public int hashCode() {
    return Objects.hash(column, prefixSize, sortOrder);
  }

  @Override
  public String toString() {
    return "IndexedColumn{" +
        "column=" + column +
        ", prefixSize=" + prefixSize +
        ", sortOrder=" + sortOrder +
        '}';
  }
}
