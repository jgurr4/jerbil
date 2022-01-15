package com.ple.jerbil.data.translator;

import com.ple.jerbil.data.Immutable;
import com.ple.jerbil.data.selectExpression.Column;
import com.ple.jerbil.data.selectExpression.Expression;
import com.ple.jerbil.data.selectExpression.booleanExpression.BooleanExpression;

/**
 * A wrapper for columns which also includes info about whether said column requires a table name syntax
 * when used in a query.
 *
 * For example:
 * "select user.name, quantity from user inner join `order` using (userId) * where user.name = 'bob';"
 * userId requires the table.column syntax because name column exists in both user and order tables.
 * But quantity doesn't require it because it exists in only one table. There also exists other conditions which can
 * cause a requiresTableName to return true for a column based on context.
 */
@Immutable
public class QueriedColumn implements Expression {

  public final Column column;
  public final String tableName;

  protected QueriedColumn(Column column, String tableName) {
    this.column = column;
    this.tableName = tableName;
  }

  public static QueriedColumn make(Column column, String tableName) {
    return new QueriedColumn(column, tableName);
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
