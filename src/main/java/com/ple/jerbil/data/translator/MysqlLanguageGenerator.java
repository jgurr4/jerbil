package com.ple.jerbil.data.translator;

import com.ple.jerbil.data.LanguageGenerator;
import com.ple.jerbil.data.query.*;
import com.ple.jerbil.data.selectExpression.*;
import com.ple.jerbil.data.selectExpression.booleanExpression.*;
import com.ple.util.*;
import org.jetbrains.annotations.NotNull;

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
    //FIXME: Ask if this is thread-safe or not. My guess is it's not because if another process uses this method and it changes the query, then that will affect all current processes.
    //GeneratorQuery.set(selectQuery);  // The reason I made this was so I could access selectQuery.fromExpression in the findMatch() method.
    IHashMap<Table, Column> matches = findMatches(selectQuery.fromExpression.tableList());
    //After I successfully obtain the matches, I need to decide if it's worth passing into each method that needs it.
    // Or if I should just use findMatch() inside those methods and pass in selectQuery.fromExpression.tableList() instead.
    return "select " + toSqlSelect(selectQuery.select) + "\n" + toSql(selectQuery.fromExpression) + "\n" + toSqlWhere(selectQuery.where) + "\n";
  }

  private IHashMap<Table, Column> findMatches(IList<Table> tableList) {
    return null;
  }


  private String toSqlWhere(BooleanExpression where) {
    if (where == null) {
      return "";
    }
    String fullWhereClause = "where ";
    fullWhereClause += toSqlBooleanExpression(where);
    if (fullWhereClause.endsWith(")")) {
      fullWhereClause = fullWhereClause.replaceAll("where \\(", "where ").replaceAll("\\)\\z", "");
    }
    return fullWhereClause;
  }

  private Boolean findMatch(Column column) {
    // TODO: Figure out how to get Query.fromExpression here so I can check all the Columns from each table to make sure
    // none of them match this column name and type.
    IArrayList<IEntry<String, Column> entries =
    if (fromList.length < 2) {
      for (Table t : fromList) {
        final IMap<String, Column> columns = t.columns;
        for (IEntry<String, Column> entry : columns) {

        }
      }
    }
    return null;
  }

  private IArrayList<Table> getFromExpressionList(FromExpression fromExpression) {
    if (fromExpression instanceof Join) {
      Join join = (Join) fromExpression;
      if (join.fe1 instanceof Join) {
        getFromExpressionList(join.fe1);
      }
    }
    return null;
  }

  private String toSqlBooleanExpression(BooleanExpression where) {
    String booleanExpressions = "";
    if (where instanceof Equals) {
      final Equals eq = (Equals) where;
      booleanExpressions += toSql(eq.e1) + " = " + toSql(eq.e2);
    } else if (where instanceof GreaterThan) {
      final GreaterThan gt = (GreaterThan) where;
      booleanExpressions += toSql(gt.e1) + " > " + toSql(gt.e2);
    } else if (where instanceof And) {
      final And and = (And) where;
      final BooleanExpression[] boolExp = and.conditions.toArray();
      booleanExpressions += "(";
      for (int i = 0; i < boolExp.length; i++) {
        if (i == boolExp.length - 1) {
          booleanExpressions += toSqlBooleanExpression(boolExp[i]);
        } else {
          booleanExpressions += toSqlBooleanExpression(boolExp[i]) + "\nand ";
        }
      }
      booleanExpressions += ")";
    } else if (where instanceof Or) {
      final Or or = (Or) where;
      final BooleanExpression[] boolExp = or.conditions.toArray();
      booleanExpressions += "(";
      for (int i = 0; i < boolExp.length; i++) {
        if (i == boolExp.length - 1) {
          booleanExpressions += toSqlBooleanExpression(boolExp[i]);
        } else {
          booleanExpressions += toSqlBooleanExpression(boolExp[i]) + " or ";
        }
      }
      booleanExpressions += ")";
    } else {
      throw new IllegalStateException("Unexpected value: " + where.getClass().getSimpleName());
    }
    return booleanExpressions;
  }

  private String toSql(Expression e) {
    final String output;
    if (e instanceof Column) {
      //TODO: Check if keyword or has space and put `backticks` around it. Once I add that feature.
      final Column s = (Column) e;
      if(findMatch(s)) {
        output = s.getTable().name + "." + s.getName();
      } else {
        output = s.getName();
      }
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
    String fullFromExpressionList = "from ";
    if (fromExpression instanceof Table) {
      Table table = (Table) fromExpression;
      fullFromExpressionList += table.name;
    } else if (fromExpression instanceof Join) {
      Join join = (Join) fromExpression;
      fullFromExpressionList += getJoinString(join);
    }
    return fullFromExpressionList;
  }

  @NotNull
  private String getJoinString(Join join) {
    String result = "";
    if (join.fe1 instanceof Join) {
      result += getJoinString((Join) join.fe1);
      Table t2 = (Table) join.fe2;
      //FIXME: using needs to choose the name of the field based on primary key of the fromexpression and if a matching field exists in compared fromexpression. If not, this needs to throw an error.
      result += "\ninner join " + t2.name + " using (" + t2.name + "Id)";
    } else {
      Table t1 = (Table) join.fe1;
      Table t2 = (Table) join.fe2;
      result += t1.name + "\ninner join " + t2.name + " using (" + t1.name + "Id)";
    }
    return result;
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
      } else if (selectArr[i] instanceof SelectAllExpression) {
        fullSelectList += "*";
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
