package com.ple.jerbil.data.selectExpression;

import com.ple.jerbil.data.selectExpression.NumericExpression.LiteralNumber;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * Literals can be used to define literal values to be used in expressions. For example if you are trying to select a
 * literal instead of a column that would look like this:
 * select 2, "myname", "02-13-2020", true; // These are all literals defined like so: Literal.make(2), Literal.make("myname"); etc...
 * LiteralBoolean, LiteralDate, LiteralString, LiteralNumber are all sub-classes.
 */
public interface Literal extends Expression {

  static LiteralString make(String s) {
    return LiteralString.make(s);
  }

  static LiteralString make(Enum<?> e) {
    return LiteralString.make(e.name());
  }

  static LiteralNumber make(int i) {
    return LiteralNumber.make(i);
  }

  static LiteralNumber make(double i) {
    return LiteralNumber.make(i);
  }

  static LiteralDate make(LocalDateTime date) {
    return LiteralDate.make(date);
  }

  static LiteralDate make(LocalTime parse) {
    return null;
  }

  static LiteralDate make(LocalDate parse) {
    return null;
  }

  static LiteralBoolean make(Boolean b) {
    return LiteralBoolean.make(b);
  }

}
