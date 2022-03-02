package com.ple.jerbil.data;

import com.ple.jerbil.data.GenericInterfaces.Immutable;
import com.ple.jerbil.data.query.Table;
import com.ple.jerbil.data.selectExpression.Column;
import com.ple.util.IArrayList;
import com.ple.util.IList;
import org.jetbrains.annotations.Nullable;

@Immutable
public class Index {
  public final IndexType type;
  public final String indexName;
  public final Table table;
  public final IList<Column> columns;
  @Nullable public final int size;
  @Nullable public final Order order;
//  @Nullable public final FkReference fkReference;

  protected Index(IndexType type, String indexName, Table table, IList<Column> columns,
                  int size,
                  @Nullable Order order) {
    this.type = type;
    this.indexName = indexName;
    this.table = table;
    this.columns = columns;
    this.size = size;
    this.order = order;
  }

  public static Index make(IndexType indexType, String indexName, Table table, int size, Order order, Column... columns) {
    return new Index(indexType, indexName, table, IArrayList.make(columns), size, order);
  }

  public static Index make(IndexType indexType, String indexName, Table table, Column... columns) {
    return new Index(indexType, indexName, table, IArrayList.make(columns), 0, null);
  }

  public static Index make(IndexType indexType, Table table, Column... columns) {
    if (indexType.equals(IndexType.primary)) {
      return new Index(indexType, "primary", table, IArrayList.make(columns), 0, null);
    }
    return new Index(indexType, DatabaseService.generateIndexName(columns),
        table, IArrayList.make(columns), 0, null);
  }

}
