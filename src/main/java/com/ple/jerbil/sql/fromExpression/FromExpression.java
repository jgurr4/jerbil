package com.ple.jerbil.sql.fromExpression;

import com.ple.jerbil.sql.StorageEngine;
import com.ple.jerbil.sql.query.PartialQuery;
import com.ple.jerbil.sql.selectExpression.Column;
import com.ple.util.IMap;

/**
 * All tables are FromExpressions, but not all FromExpressions are Tables.
 * For instance FromExpressions can be subqueries, joins and tables. Joins join two fromExpressions together.
 * FromExpressions are defined as anything you can put to the right of a From in sql statement.
 */
public abstract class FromExpression extends PartialQuery {

  public final Table table;

  abstract protected void diffJoin();

  public String join() {
   //fill with 2 common parts.
   diffJoin();
   return null;
  }


  protected FromExpression(Table table) {
    super(table);
    this.table = table;
  }

  protected FromExpression getFromExpression() {
    return this;
  }

}
