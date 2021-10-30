package com.ple.jerbil.data.translator;

import com.ple.jerbil.data.LanguageGenerator;
import com.ple.jerbil.data.query.*;
import com.ple.jerbil.data.selectExpression.SelectExpression;
import com.ple.jerbil.data.selectExpression.booleanExpression.BooleanExpression;
import com.ple.jerbil.data.selectExpression.booleanExpression.Equals;
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
    return "select " + toSqlSelect(selectQuery.select) + " " + toSql(selectQuery.fromExpression) + " " + toSqlWhere(selectQuery.where);
  }

  private String toSqlWhere(QueryList<BooleanExpression> where) {
    String fullWhereList = "";
    for (int i = 0; i < where.toArray().length; i++) {
      switch (where.values[i].getClass().getSimpleName()) {
        case "Equals" -> {
          Equals eq = (Equals) where.values[i];
          fullWhereList += eq.e1 + " = " + eq.e2;
        }
        case "GreaterThan" -> {

        }
        default -> throw new IllegalStateException("Unexpected value: " + where.values[0].getClass().getSimpleName());
      }
    }
    return fullWhereList;
  }

  private String toSql(FromExpression fromExpression) {
    return null;
  }

  private String toSqlSelect(IList<SelectExpression> select) {
    final SelectExpression[] selectArr = select.toArray();
    String fullSelectList = "";
    if (selectArr.length == 0) {
      return "*";
    }
    for (int i = 0; i < selectArr.length; i++) {
      if (selectArr.length == i + 1) {
        fullSelectList += " " + selectArr[i];
      } else {
        fullSelectList += " " + selectArr[i] + ",";
      }
    }
    return fullSelectList;
  }

  public String toSql(UpdateQuery updateQuery) {
    return null;
  }

  public String toSql(DeleteQuery deleteQuery) {
    return null;
  }
}
