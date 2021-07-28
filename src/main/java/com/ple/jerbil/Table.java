package com.ple.jerbil;

import com.ple.jerbil.expression.BooleanExpression;
import com.ple.jerbil.expression.SelectExpression;
import com.ple.jerbil.expression.TableExpression;
import com.ple.jerbil.query.PartialQuery;
import com.ple.jerbil.query.CompleteQuery;
import com.ple.jerbil.query.QueryType;

@DelayedImmutable
public abstract class Table implements TableExpression {

  protected StorageEngine engine = StorageEngine.simple;
  private final String tableName;

  public Table(String name) {
    this.tableName = name;
  }

  public PartialQuery where(BooleanExpression condition) {
    return PartialQuery.make(this).where(condition);
  }

  public CompleteQuery select(SelectExpression... expression) {
    return CompleteQuery.make(QueryType.select);
  }

  public CompleteQuery join(TableExpression... tables) {
    return null;
  }

  public CompleteQuery insert() {
    return CompleteQuery.make(QueryType.insert);
  }

  public CompleteQuery create() {
    return null;
  }

}
