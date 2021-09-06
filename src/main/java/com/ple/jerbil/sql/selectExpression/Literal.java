package com.ple.jerbil.sql.selectExpression;

import java.util.List;

/**
 * Literals can be used to define literal values to be used in expressions. For example if you are trying to select a
 * literal instead of a column that would look like this:
 * select 2, "myname", "02-13-2020", true; // These are all literals defined like so: Literal.make(2), Literal.make("myname"); etc...
 * LiteralBoolean, LiteralDate, LiteralString, LiteralNumber are all sub-classes.
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
