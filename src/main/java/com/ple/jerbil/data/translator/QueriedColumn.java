package com.ple.jerbil.data.translator;

import com.ple.jerbil.data.Immutable;
import com.ple.jerbil.data.selectExpression.Column;
import com.ple.jerbil.data.selectExpression.Expression;
import com.ple.jerbil.data.selectExpression.booleanExpression.BooleanExpression;


@Immutable
public class QueriedColumn implements Expression {

  public final Column column;
  public final Boolean requiresTableName;

  protected QueriedColumn(Column column, boolean requiresTableName) {
    this.column = column;
    this.requiresTableName = requiresTableName;
  }

  public static QueriedColumn make(Column column, boolean requiresTableName) {
    return new QueriedColumn(column, requiresTableName);
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
