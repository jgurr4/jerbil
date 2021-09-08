package com.ple.jerbil.sql.selectExpression;

import com.ple.jerbil.sql.Immutable;
import com.ple.jerbil.sql.query.CompleteQuery;

/**
 * SelectExpressions take many forms in sql. They can be included in various places in the statement. Such as following
 * the select, where, having, group by clause etc... Expressions can be columns, boolean expressions, numeric expressions, Literals, subqueries etc...
 * see: https://dev.mysql.com/doc/refman/8.0/en/expressions.html
 * SelectExpressions include normal expressions as well as aliased expressions which is why it is
 * inherited by Expressions and AliasedExpressions. Any methods which occur in both Expressions
 * and AliasedExpressions should be put in here.
 */
@Immutable
public interface SelectExpression {

  public final static SelectExpression selectAll = SelectAllExpression.make();

  public CompleteQuery select();

}
