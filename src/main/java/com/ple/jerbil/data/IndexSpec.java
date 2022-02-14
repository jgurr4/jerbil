package com.ple.jerbil.data;

import com.ple.jerbil.data.selectExpression.Column;
import com.ple.util.IList;

public class IndexSpec {

  public final Index index;
  public final IList<Column> columns;
  public final int size;
  public final IndexSort order;

  protected IndexSpec(Index index, IList<Column> columns, int size, IndexSort order) {
    this.index = index;
    this.columns = columns;
    this.size = size;
    this.order = order;
  }

  public static IndexSpec make(Index index, IList<Column> columns, int size, IndexSort order) {
    return new IndexSpec(index, columns, size, order);
  }

  public static IndexSpec make(Index index, IList<Column> columns) {
    return new IndexSpec(index, columns, 0, null);
  }

}
