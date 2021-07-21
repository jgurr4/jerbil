package com.ple.jerbil;

import com.ple.jerbil.expression.BooleanExpression;
import com.ple.jerbil.expression.Expression;
import com.ple.jerbil.expression.SelectExpression;
import com.ple.jerbil.expression.TableExpression;

@DelayedImmutable
public abstract class Table implements TableExpression {

  protected StorageEngine engine = StorageEngine.simple;
  private final String tableName;

  public Table(String name) {
    this.tableName = name;
  }

  public Query where(BooleanExpression condition) {

    System.out.println(condition);
    return null;
  }

  public Query select(SelectExpression... expression) {

    return null;
  }

  public Query join(TableExpression... tables) {
    return null;
  }

  public Query insert() {
    return Query.from(QueryType.insert);
  }

  public Query create() {
    return null;
  }

}
