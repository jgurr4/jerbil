package com.ple.jerbil.data.sync;

import com.ple.jerbil.data.GenericInterfaces.Immutable;
import com.ple.jerbil.data.IndexedColumn;
import com.ple.jerbil.data.SortOrder;
import com.ple.jerbil.data.selectExpression.Column;
import org.jetbrains.annotations.Nullable;

@Immutable
public class IndexedColumnDiff implements Diff<IndexedColumn> {

  @Nullable public final ScalarDiff<Column> column;
  @Nullable public final ScalarDiff<Integer> prefixSize;
  @Nullable public final ScalarDiff<SortOrder> sortOrder;

  protected IndexedColumnDiff(@Nullable ScalarDiff<Column> column, @Nullable ScalarDiff<Integer> prefixSize,
      @Nullable ScalarDiff<SortOrder> sortOrder) {
    this.column = column;
    this.prefixSize = prefixSize;
    this.sortOrder = sortOrder;
  }

  public static IndexedColumnDiff make(@Nullable ScalarDiff<Column> column, @Nullable ScalarDiff<Integer> prefixSize,
                                       @Nullable ScalarDiff<SortOrder> sortOrder) {
    return new IndexedColumnDiff(column, prefixSize, sortOrder);
  }

  @Override
  public int getTotalDiffs() {
    return 0;
  }
}
