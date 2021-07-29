package com.ple.jerbil.sql;

import com.ple.jerbil.sql.expression.*;
import com.ple.jerbil.sql.query.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@DelayedImmutable
public abstract class Table implements TableExpression {

  protected StorageEngine engine = StorageEngine.simple;
  private final String tableName;

  public Table(String name) {
    this.tableName = name;
  }

  public PartialQuery where(BooleanExpression condition) {
    return PartialQuery.make(this, condition);
  }

  public SelectQuery select(SelectExpression... expressions) {
    return SelectQuery.make(expressions, this, QueryType.select);
  }

  public CompleteQuery join(TableExpression... tables) {
    return null;
  }

  public InsertQuery insert() {
    return InsertQuery.make(Collections.emptyList(), this, QueryType.insert);
  }

  public CompleteQuery create() {
    return null;
  }

}
