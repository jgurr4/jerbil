package com.ple.jerbil.sql.query;


import com.ple.jerbil.sql.Immutable;
import com.ple.jerbil.sql.fromExpression.Table;
import com.ple.jerbil.sql.fromExpression.FromExpression;
import com.ple.jerbil.sql.expression.SelectExpression;
import com.ple.util.IArrayList;
import org.jetbrains.annotations.Nullable;

@Immutable
public class Query {

  @Nullable
  protected final Table table;

  protected Query(Table table) {
    this.table = table;
  }

  public CompleteQuery select(SelectExpression... expressions) {
    return SelectQuery.make(IArrayList.make(expressions), table);
  }

}
