package com.ple.jerbil.data.selectExpression;

import com.ple.jerbil.data.selectExpression.NumericExpression.LiteralNumber;

import java.time.LocalDateTime;

/**
 * Literals can be used to define literal values to be used in expressions. For example if you are trying to select a
 * literal instead of a column that would look like this:
 * select 2, "myname", "02-13-2020", true; // These are all literals defined like so: Literal.make(2), Literal.make("myname"); etc...
 * LiteralBoolean, LiteralDate, LiteralString, LiteralNumber are all sub-classes.
 */
public interface Literal extends Expression {

  public static LiteralString make(String s) {
    return LiteralString.make(s);
  }

  public static LiteralNumber make(int i) {
    return LiteralNumber.make(i);
  }

  public static LiteralNumber make(double i) {
    return LiteralNumber.make(i);
  }

  public static LiteralDate make(LocalDateTime date) {
    return null;
  }

  public static LiteralBoolean make(Boolean b) {
    return null;
  }


}
