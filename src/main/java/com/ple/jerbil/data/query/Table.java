package com.ple.jerbil.data.query;

import com.ple.jerbil.data.DelayedImmutable;
import com.ple.jerbil.data.StorageEngine;
import com.ple.jerbil.data.selectExpression.*;
import com.ple.jerbil.data.selectExpression.booleanExpression.BooleanExpression;
import com.ple.util.IArrayList;
import com.ple.util.IHashMap;
import com.ple.util.IList;
import com.ple.util.IMap;


/**
 * Table is a database Object which contains columns.
 */
@DelayedImmutable
public class Table extends FromExpression {

  public final StorageEngine engine;
  public final String name;
  public IMap<String, Column> columns;

  protected Table(String name) {
    this(StorageEngine.simple, name, IHashMap.empty);
  }

  protected Table(StorageEngine engine, String name, IMap<String, Column> columns) {
    this.engine = engine;
    this.name = name;
    this.columns = columns;
  }

  private static Table make(StorageEngine engine, String name, IMap<String, Column> columns) {
    return new Table(engine, name, columns);
  }

  public String toSql() {
    return "create table " + name + " (" + ")";
  }

  public QueryWithFrom where(BooleanExpression condition) {
    return QueryWithFrom.make(this).where(condition);
  }

  public QueryWithFrom join(FromExpression... tables) {
    FromExpression result = this;
    for (FromExpression table : tables) {
      result = Join.make(result, table);
    }
    return QueryWithFrom.make(result);
  }

  public CreateQuery create() {
    return CreateQuery.make(this);
  }

  public Table set(Column column) {
    return null;
  }

  public Table remove(Column column) {
    return null;
  }

  public PartialInsertQuery insert() {
    return PartialInsertQuery.make(this);
  }

  public CompleteQuery select(CountAgg agg) {
    return SelectQuery.make(this, IArrayList.make(agg));
  }

  public SelectQuery select(AliasedExpression... aliasedExpressions) {
    return SelectQuery.make(this, IArrayList.make(aliasedExpressions));
  }

  @Override
  protected void diffJoin() {
  }

  @Override
  public IList<Table> tableList() {
    return IArrayList.make(this);
  }

  public void add(Column column) {
    final IMap<String, Column> newColumns = columns.put(column.getName(), column);
    columns = newColumns;
  }

}
