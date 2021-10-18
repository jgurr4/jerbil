package com.ple.jerbil.sql.query;

import com.ple.jerbil.sql.Immutable;
import com.ple.jerbil.sql.selectExpression.Column;
import com.ple.jerbil.sql.selectExpression.Literal;
import com.ple.jerbil.sql.fromExpression.Table;

import java.util.List;

@Immutable
public class PartialQueryWithValues extends PartialQuery {
//For use with PartialUpdate, PartialInsert and PartialReplace queries, because they require .set method to list columns and the values to put in/replace.

  public CompleteQuery set(List<Column> columns, List<List<String>> values) {
    return null;
  }

  public CompleteQuery set(Column column, Literal value) {
    return CompleteQuery.make(null, column, value, null);
  }

}
