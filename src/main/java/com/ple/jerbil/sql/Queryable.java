package com.ple.jerbil.sql;

import com.ple.jerbil.sql.expression.SelectExpression;
import com.ple.jerbil.sql.fromExpression.FromExpression;
import com.ple.jerbil.sql.query.InsertQuery;
import com.ple.jerbil.sql.query.SelectQuery;
import com.ple.util.IArrayList;

@Immutable
public abstract class Queryable {

  public SelectQuery select(SelectExpression... expressions) {
    return SelectQuery.make(IArrayList.make(expressions), getFromExpression());
  }

  public InsertQuery insert() {
    return InsertQuery.make(IArrayList.make(), getFromExpression());
  }

  protected abstract FromExpression getFromExpression();


}
