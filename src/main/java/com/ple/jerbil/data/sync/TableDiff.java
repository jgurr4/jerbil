package com.ple.jerbil.data.sync;

import com.ple.jerbil.data.StorageEngine;
import com.ple.jerbil.data.query.Table;
import com.ple.jerbil.data.selectExpression.Column;
import com.ple.jerbil.data.selectExpression.Expression;
import com.ple.util.IList;
import com.ple.util.IMap;

public class TableDiff implements Diff<Table> {

  public final ScalarDiff<String> name;
  public final VectorDiff<Column> columns;
  public final ScalarDiff<StorageEngine> storageEngine;
//  public final ScalarDiff<Expression> constraint;
//  public final ScalarDiff<String> comment;

  protected TableDiff(ScalarDiff<String> name, VectorDiff<Column> columns, ScalarDiff<StorageEngine> storageEngine) {
    this.name = name;
    this.columns = columns;
    this.storageEngine = storageEngine;
//    this.constraint = constraint;
//    this.comment = comment;
  }

  public static TableDiff make(ScalarDiff<String> name, VectorDiff<Column> columns,
                               ScalarDiff<StorageEngine> storageEngine) {
    return new TableDiff(name, columns, storageEngine);
  }

  @Override
  public int getTotalDiffs() {
    return 0;
  }

}
