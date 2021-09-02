package com.ple.jerbil.sql.selectExpression;

import java.util.List;

/**
 * Literals define all the literal
 * select * from table where name = 'bob';
 * In the example above name is a column. '=' is a booleanExpression, and 'bob' is a Literal String.
 * Select 5 + 5; // In this example 5 is literal int.
 * LiteralBoolean, LiteralDate, LiteralString, LiteralNumber are all classes
 */
public interface Literal extends Expression {

  public static Literal make(String string) {
    return null;
  }

  public static Literal make(List list) {
    return null;
  }

  public static Literal make(int i) {
    return null;
  }


}
