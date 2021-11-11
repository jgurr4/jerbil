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
    return "select " + toSqlSelect(selectQuery.select) + "\n" + toSql(selectQuery.fromExpression) + "\n" + toSqlWhere(selectQuery.where) + "\n";
  }


  private String toSqlWhere(BooleanExpression where) {
    if (where == null) {
      return "";
    }
    String fullWhereList = "where ";
    if (where instanceof Equals) {
      final Equals eq = (Equals) where;
      fullWhereList += toSql(eq.e1) + " = " + toSql(eq.e2);
    } else if (where instanceof GreaterThan) {
      //TODO: put GreaterThan handling here
    } else {
      throw new IllegalStateException("Unexpected value: " + where.getClass().getSimpleName());
    }
    return fullWhereList;
  }

  private String toSql(Expression e) {
    final String output;
    if (e instanceof Column) {
      //TODO: Check if keyword or has space and put `backticks` around it. Once I add that feature.
      final Column s = (Column) e;
      output = s.getName();
    } else if (e instanceof LiteralString) {
      final LiteralString litStr = (LiteralString) e;
      output = "'" + litStr.value + "'";
    } else if (e instanceof LiteralNumber) {
      final LiteralNumber n = (LiteralNumber) e;
      output = n.value.toString();
    } else {
      throw new RuntimeException("Unknown Expression: " + e.getClass().getName());
    }
    return output;
  }

  private String toSql(FromExpression fromExpression) {
    String fullFromExpressionList = "";
    if (fromExpression instanceof Table) {
      Table table = (Table) fromExpression;
      fullFromExpressionList += "from " + table.name + "";
    }
    return fullFromExpressionList;
  }

  private String toSqlSelect(IList<SelectExpression> select) {
    final SelectExpression[] selectArr = select.toArray();
    String fullSelectList = "";
    if (selectArr.length == 0) {
      return "*";
    }
    for (int i = 0; i < selectArr.length; i++) {
      if (selectArr[i] instanceof NumericColumn) {
        fullSelectList += ((NumericColumn) selectArr[i]).name;
      } else if (selectArr[i] instanceof LiteralNumber) {
        // TODO: Handle Literal values here.
      }
      if (selectArr.length != i + 1) {
        fullSelectList += ", ";
      } else {
        fullSelectList += "";
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