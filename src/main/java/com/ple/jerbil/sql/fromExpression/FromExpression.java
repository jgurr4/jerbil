package com.ple.jerbil.sql.fromExpression;

import com.ple.jerbil.sql.query.PartialQuery;

/**
 * All tables are FromExpressions, but not all FromExpressions are Tables.
 * For instance FromExpressions can be subqueries, joins and tables. Joins join two fromExpressions together.
 * FromExpressions are defined as anything you can put to the right of a From in sql statement.
 */
public class FromExpression extends PartialQuery {

  public final Table table;

  abstract protected void diffJoin();

  public String join() {
   //fill with 2 common parts.
   diffJoin();
   return null;
  }


  protected FromExpression(Table table) {
    this.table = table;
  }

  protected FromExpression getFromExpression() {
    return this;
  }

}
