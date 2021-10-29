package com.ple.jerbil.data.translator;

import com.ple.jerbil.data.LanguageGenerator;
import com.ple.jerbil.data.query.*;
import com.ple.jerbil.data.selectExpression.SelectExpression;
import com.ple.jerbil.data.selectExpression.booleanExpression.BooleanExpression;
import com.ple.util.IList;

public class MysqlLanguageGenerator implements LanguageGenerator {
  public static LanguageGenerator make() {
    return new MysqlLanguageGenerator();
  }

  @Override
  public String toSql(CompleteQuery completeQuery) {
    final String sql;
    switch (completeQuery.queryType) {
      case select -> {
        sql = toSql((SelectQuery) completeQuery);
      }
      case delete -> {
        sql = toSql((DeleteQuery) completeQuery);
      }
      case update -> {
        sql = toSql((UpdateQuery) completeQuery);
      }
      default -> throw new IllegalStateException("Unexpected value: " + completeQuery.queryType);
    }
    return sql;
  }

  public String toSql(SelectQuery selectQuery) {
    return "select " + toSqlSelect(selectQuery.select) + toSql(selectQuery.fromExpression) + toSqlWhere(selectQuery.where);
  }

  private String toSqlWhere(IList<BooleanExpression> where) {
    //TODO: Figure out how to turn the list of where booleanExpressions into a normal String. And any common code should
    // be placed in its own method for reuse in other toSql methods.
    return ;
  }

  private String toSql(FromExpression fromExpression) {
    return null;
  }

  private String toSqlSelect(IList<SelectExpression> select) {
    return null;
  }

  public String toSql(UpdateQuery updateQuery) {
    return null;
  }

  public String toSql(DeleteQuery deleteQuery) {
    return null;
  }
}
