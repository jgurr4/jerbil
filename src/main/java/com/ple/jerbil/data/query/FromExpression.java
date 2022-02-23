package com.ple.jerbil.data.query;

import com.ple.jerbil.data.Immutable;
import com.ple.jerbil.data.PotentialQuery;
import com.ple.jerbil.data.selectExpression.SelectExpression;
import com.ple.util.IArrayList;
import com.ple.util.IList;

/**
 * All tables are FromExpressions, but not all FromExpressions are Tables.
 * For instance FromExpressions can be subqueries, joins and tables. Joins join two fromExpressions together.
 * FromExpressions are defined as anything you can put to the right of a From in sql statement.
 */
@Immutable
public abstract class FromExpression extends PotentialQuery {

  abstract protected void diffJoin();

  public FromExpression join() {
    //fill with 2 common parts.
    diffJoin();
    return null;
  }

  @Override
  public SelectQuery select(SelectExpression... selectExpressions) {
    return new SelectQuery(null, this, QueryType.select, IArrayList.make(selectExpressions),
        null, null, null, null, null, null);
  }

  public abstract IList<TableContainer> tableList();

}
