package com.ple.jerbil.sql.fromExpression;

import com.ple.jerbil.sql.Queryable;
import com.ple.jerbil.sql.query.InsertQuery;
import com.ple.util.IArrayList;

public class FromExpression extends Queryable {

  @Override
  protected FromExpression getFromExpression() {
    return this;
  }

}
