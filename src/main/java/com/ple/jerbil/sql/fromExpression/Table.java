package com.ple.jerbil.sql.fromExpression;

import com.ple.jerbil.sql.DelayedImmutable;
import com.ple.jerbil.sql.StorageEngine;
import com.ple.jerbil.sql.selectExpression.*;
import com.ple.jerbil.sql.selectExpression.booleanExpression.BooleanExpression;
import com.ple.jerbil.sql.query.*;
import com.ple.util.IHashMap;
import com.ple.util.IMap;


/**
 * Table is a database Object which contains columns.
 */
@DelayedImmutable
public class Table extends FromExpression {

  public final StorageEngine engine;
  public final String name;
  public final IMap<String, Column> columns;

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

  public CompleteQuery join(Table... tables) {
    return null;
  }

  public CompleteQuery create() {
    return null;
  }

  public Table set(Column column) {
    final IMap<String, Column> newColumns = columns.put(column.name, column);
    return new Table(engine, name, newColumns);
  }

  public Table remove(Column column) {
    final IMap<String, Column> newColumns = columns.remove(column.name);
    return new Table(engine, name, newColumns);
  }

  public PartialInsertQuery insert() {
    return PartialInsertQuery.make(this);
  }

  public CompleteQuery select(Agg agg) {
    return null;
  }

  public CompleteQuery select(Column column) {
    return null;
  }

  public CompleteQuery select(AliasedExpression... aliasedExpressions) {
    return null;
  }

}
