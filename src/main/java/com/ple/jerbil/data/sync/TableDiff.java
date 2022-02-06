package com.ple.jerbil.data.sync;

import com.ple.jerbil.data.selectExpression.Column;
import com.ple.util.IList;
import com.ple.util.IMap;

public class TableDiff implements Diff {

  public static TableDiff combineDiffs(IList<Column> columns, IList<Column> columns1) {
    return null;
  }

  @Override
  public IMap<TableProps, Object> create() {
    return null;
  }

  @Override
  public IMap<TableProps, Object> delete() {
    return null;
  }

  @Override
  public IMap<TableProps, Object> update() {
    return null;
  }

}
