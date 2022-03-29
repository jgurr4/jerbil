package com.ple.jerbil.data;

import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class IndexedColumn {
  public static final IndexedColumn[] emptyArray = new IndexedColumn[0];
  public final String columnName;
  public final int prefixSize;
  @Nullable public final SortOrder sortOrder;

  protected IndexedColumn(String columnName, int prefixSize, @Nullable SortOrder sortOrder) {
    this.columnName = columnName;
    this.prefixSize = prefixSize;
    this.sortOrder = sortOrder;
  }

  public static IndexedColumn make(String column, int prefixSize, @Nullable SortOrder sortOrder) {
    return new IndexedColumn(column, prefixSize, sortOrder);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof IndexedColumn)) return false;
    IndexedColumn that = (IndexedColumn) o;
    return prefixSize == that.prefixSize && columnName.equals(that.columnName) && sortOrder == that.sortOrder;
  }

  @Override
  public int hashCode() {
    return Objects.hash(columnName, prefixSize, sortOrder);
  }

  @Override
  public String toString() {
    return "IndexedColumn{" +
        "column=" + columnName +
        ", prefixSize=" + prefixSize +
        ", sortOrder=" + sortOrder +
        '}';
  }
}
