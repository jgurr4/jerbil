package com.ple.jerbil.data.sync;

import com.ple.util.Immutable;
import com.ple.jerbil.data.IndexedColumn;
import com.ple.jerbil.data.SortOrder;
import com.ple.jerbil.data.selectExpression.Column;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

@Immutable
public class IndexedColumnDiff implements Diff<IndexedColumn> {

  @Nullable public final ScalarDiff<Column> column;
  @Nullable public final ScalarDiff<Integer> prefixSize;
  @Nullable public final ScalarDiff<SortOrder> sortOrder;
  public final IndexedColumn indexedColumnA;
  public final IndexedColumn indexedColumnB;

  protected IndexedColumnDiff(@Nullable ScalarDiff<Column> column, @Nullable ScalarDiff<Integer> prefixSize,
                              @Nullable ScalarDiff<SortOrder> sortOrder,
                              IndexedColumn indexedColumnA, IndexedColumn indexedColumnB) {
    this.column = column;
    this.prefixSize = prefixSize;
    this.sortOrder = sortOrder;
    this.indexedColumnA = indexedColumnA;
    this.indexedColumnB = indexedColumnB;
  }

  public static IndexedColumnDiff make(@Nullable ScalarDiff<Column> column, @Nullable ScalarDiff<Integer> prefixSize,
                                       @Nullable ScalarDiff<SortOrder> sortOrder, IndexedColumn indexedColumnA,
                                       IndexedColumn indexedColumnB) {
    return new IndexedColumnDiff(column, prefixSize, sortOrder, indexedColumnA, indexedColumnB);
  }

  @Override
  public int getTotalDiffs() {
    return 0;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof IndexedColumnDiff)) return false;
    IndexedColumnDiff that = (IndexedColumnDiff) o;
    return Objects.equals(column, that.column) && Objects.equals(prefixSize,
        that.prefixSize) && Objects.equals(sortOrder, that.sortOrder);
  }

  @Override
  public int hashCode() {
    return Objects.hash(column, prefixSize, sortOrder);
  }

  @Override
  public String toString() {
    String cols = "";
    String preSize = "";
    String sOrder = "";
    if (column != null) {
      cols = "\n  column=\n    left: " + column.before + "\n    right: " + column.after;
    }
    if (prefixSize != null) {
      preSize = "\n  prefixSize=\n    left: " + prefixSize.before + "\n    right: " + prefixSize.after;
    }
    if (sortOrder != null) {
      sOrder = "\n  sortOrder=\n    left: " + sortOrder.before + "\n    right: " + sortOrder.after;
    }
    return "IndexedColumnDiff{ leftName: " + indexedColumnA.column.columnName + "  rightName: " + indexedColumnB.column.columnName +
        cols +
        preSize +
        sOrder +
        '}';
  }

  @Override
  public IndexedColumnDiff filter(DdlOption ddlOption) {
    ScalarDiff<Column> newColumn = null;
    ScalarDiff<Integer> newPrefixSize = null;
    ScalarDiff<SortOrder> newSortOrder = null;
    if (column != null) {
      newColumn = column.filter(ddlOption);
    }
    if (prefixSize != null) {
      newPrefixSize = prefixSize.filter(ddlOption);
    }
    if (sortOrder != null) {
      newSortOrder = sortOrder.filter(ddlOption);
    }
    return new IndexedColumnDiff(newColumn, newPrefixSize, newSortOrder, indexedColumnA, indexedColumnB);
  }
}
