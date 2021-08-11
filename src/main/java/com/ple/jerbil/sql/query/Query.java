package com.ple.jerbil.sql.query;


import com.ple.jerbil.sql.Immutable;
import com.ple.jerbil.sql.Queryable;
import com.ple.jerbil.sql.fromExpression.FromExpression;
import org.jetbrains.annotations.Nullable;

@Immutable
public class Query extends Queryable {

  @Nullable
  protected final FromExpression fromExpression;

  protected Query(FromExpression fromExpression) {
    this.fromExpression = fromExpression;
  }

  @Override
  protected FromExpression getFromExpression() {
    return fromExpression;
  }

}
