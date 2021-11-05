package com.ple.jerbil.data.translator;

import com.ple.jerbil.data.LanguageGenerator;
import com.ple.jerbil.data.query.*;
import com.ple.jerbil.data.selectExpression.*;
import com.ple.jerbil.data.selectExpression.booleanExpression.BooleanExpression;
import com.ple.jerbil.data.selectExpression.booleanExpression.Equals;
import com.ple.jerbil.data.selectExpression.booleanExpression.GreaterThan;
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
    //TODO: Find out why fromExpression and where are null.
    return "select " + toSqlSelect(selectQuery.select) + " " + toSql(selectQuery.fromExpression) + " " + toSqlWhere(selectQuery.where);
  }

  private String toSqlWhere(BooleanExpression where) {
    if (where == null) {
      return "";
    }
    String fullWhereList = "where ";
    // Switch pattern matching is part of java 17 preview, but it might be changed later. Most likely it will stay though. It does same as reflection but it's better at compile-time checking.
    if (where instanceof Equals) {//TODO: Try to get rid of this line and make eq.e1 work without type casting. Ask about it, because I couldn't get it to work without this.
      Equals eq = (Equals) where; //TODO: Ask if there is a way to do this without type casting.
      fullWhereList += ((StringColumn) eq.e1).name + " = " + ((LiteralString) eq.e2).value + " ";  //In this case query should be: "select columnName from tableName where e1 = e2;
    } else if (where instanceof GreaterThan) {
      //TODO: put GreaterThan handling here
    } else {
      throw new IllegalStateException("Unexpected value: " + where.getClass().getSimpleName());
    }
    return fullWhereList;
  }

  private String toSql(FromExpression fromExpression) {
    String fullFromExpressionList = "";
    if (fromExpression instanceof Table) {
      Table table = (Table) fromExpression;
      fullFromExpressionList += "from " + table.name + " ";
    }
    return fullFromExpressionList;
  }

  private String toSqlSelect(IList<SelectExpression> select) {
    final SelectExpression[] selectArr = select.toArray();
    String fullSelectList = "";
    if (selectArr.length == 0) {
      return "*";
    }
    String className = "";
    for (int i = 0; i < selectArr.length; i++) {
      if (selectArr[i] instanceof NumericColumn) {
        // TODO: Find out if casting is required here or not.
        fullSelectList += ((NumericColumn) selectArr[i]).name; //In this case query should be: "select columnName "
      } else if (selectArr[i] instanceof LiteralNumber) {
        // TODO: Handle proper Literal values here.
      }
      if (selectArr.length != i + 1) {
        fullSelectList += ", ";
      } else {
        fullSelectList += " ";
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
