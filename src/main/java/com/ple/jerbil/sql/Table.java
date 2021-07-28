package com.ple.jerbil.sql;

import com.ple.jerbil.sql.expression.BooleanExpression;
import com.ple.jerbil.sql.expression.SelectExpression;
import com.ple.jerbil.sql.expression.TableExpression;
import com.ple.jerbil.sql.query.PartialQuery;
import com.ple.jerbil.sql.query.CompleteQuery;
import com.ple.jerbil.sql.query.QueryType;

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

  public CompleteQuery select(SelectExpression... expressions) {
    return CompleteQuery.make(QueryType.select, this, );
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
