package com.ple.jerbil.data.sync;

import com.ple.jerbil.data.GenericInterfaces.Immutable;
import com.ple.jerbil.data.StorageEngine;
import com.ple.jerbil.data.query.TableContainer;
import com.ple.jerbil.data.selectExpression.Column;
import com.ple.util.IList;

@Immutable
public class TableDiff implements Diff<TableContainer> {

  public static Diff<TableContainer>[] empty = new TableDiff[0];
  public final ScalarDiff<String> tableName;
  public final VectorDiff<Column> columns;
  public final IList<ColumnDiff> columnDiffs;
  public final ScalarDiff<StorageEngine> storageEngine;
//  public final ScalarDiff<Expression> constraint;
//  public final ScalarDiff<String> comment;

  protected TableDiff(ScalarDiff<String> tableName, VectorDiff<Column> columns,
                      IList<ColumnDiff> columnDiffs,
                      ScalarDiff<StorageEngine> storageEngine) {
    this.tableName = tableName;
    this.columns = columns;
    this.columnDiffs = columnDiffs;
    this.storageEngine = storageEngine;
//    this.constraint = constraint;
//    this.comment = comment;
  }

  public static TableDiff make(ScalarDiff<String> name, VectorDiff<Column> columns,
                               IList<ColumnDiff> columnDiffs, ScalarDiff<StorageEngine> storageEngine) {
    return new TableDiff(name, columns, columnDiffs, storageEngine);
  }

  @Override
  public int getTotalDiffs() {
    return 0;
  }

}
