package com.ple.jerbil.data.translator;

import com.ple.jerbil.data.Immutable;
import com.ple.jerbil.data.query.Table;
import com.ple.jerbil.data.selectExpression.Column;
import com.ple.jerbil.data.selectExpression.Expression;
import com.ple.jerbil.data.selectExpression.booleanExpression.BooleanExpression;

@Immutable
public class QueriedColumn<T extends Column> implements Column<T>, Expression {

  public final Column<T> column;
  public final Boolean requiresTableName;

  protected QueriedColumn(Column<T> column, Boolean requiresTableName) {
    this.column = column;
    this.requiresTableName = requiresTableName;
  }

  public QueriedColumn<T> make(Column<T> column, Boolean requiresTableName) {
    return new QueriedColumn<>(column, requiresTableName);
  }

  @Override
  public String getName() {
    return column.getName();
  }

  @Override
  public Table getTable() {
    return column.getTable();
  }

  @Override
  public T primary() {
    return column.primary();
  }

  @Override
  public T indexed() {
    return column.indexed();
  }

  @Override
  public BooleanExpression isGreaterThan(Expression i) {
    return null;
  }

  @Override
  public BooleanExpression isLessThan(Expression i) {
    return null;
  }

  @Override
  public BooleanExpression eq(Expression item) {
    return null;
  }

}
