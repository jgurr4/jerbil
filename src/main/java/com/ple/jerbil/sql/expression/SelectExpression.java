package com.ple.jerbil.sql.expression;

import com.ple.jerbil.sql.Immutable;
import com.ple.jerbil.sql.query.CompleteQuery;
import com.ple.jerbil.sql.query.PartialSelectQuery;
import com.ple.jerbil.sql.query.SelectQuery;

// Can be normal expression or aliased expression. normal expressions can be calculated to each other without problems. Aliased expressions cannot.
// For example: 'SELECT 2 + 2' works, but 'SELECT 2 AS number + 2' Doesn't work.
// Select Expression is a single expression, but it is often in a list of expressions like so:
//Example: 'SELECT name, email, phone AS phoneNumber;
@Immutable
public class SelectExpression {

  public final static SelectExpression selectAll = SelectAllExpression.make();

  public CompleteQuery select() {
    return CompleteQuery.make();
  }

}
