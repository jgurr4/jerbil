package com.ple.jerbil.sql.fromExpression;

import com.ple.jerbil.sql.DelayedImmutable;
import com.ple.jerbil.sql.StorageEngine;
import com.ple.jerbil.sql.expression.*;
import com.ple.jerbil.sql.query.*;
import com.ple.util.IArrayList;
import com.ple.util.IList;

import java.util.Collections;

@DelayedImmutable
public abstract class Table extends FromExpression {

  protected StorageEngine engine = StorageEngine.simple;
  private final String tableName;

  public Table(String name) {
    this.tableName = name;
  }

  public QueryWithFrom where(BooleanExpression condition) {
    return QueryWithFrom.make(this, condition);
  }

  public SelectQuery select(SelectExpression expressions) {
    return SelectQuery.make(IArrayList.make(expressions), this);
  }

  public CompleteQuery join(FromExpression... tables) {
    return null;
  }

  public InsertQuery insert() {
    return InsertQuery.make(IArrayList.make(), this);
  }

  public CompleteQuery create() {
    return null;
  }

}
