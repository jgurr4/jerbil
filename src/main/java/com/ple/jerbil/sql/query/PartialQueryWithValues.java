package com.ple.jerbil.sql.query;

import com.ple.jerbil.sql.Immutable;
import com.ple.jerbil.sql.expression.Column;
import com.ple.jerbil.sql.expression.Literal;
import com.ple.jerbil.sql.fromExpression.Table;
import com.ple.util.IArrayList;

import java.util.List;

@Immutable
public class PartialQueryWithValues extends PartialQuery {
//For use with PartialUpdate, PartialInsert and PartialReplace queries, because they require .set method to list columns and the values to put in/replace.

  protected PartialQueryWithValues(Table table) {
    super(table);
  }


  public CompleteQuery set(Column column, Literal value) {
    return CompleteQuery.make(this.table, column, value);
  }

}
