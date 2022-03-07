package com.ple.jerbil.data.selectExpression;

import com.ple.util.Immutable;
import com.ple.jerbil.data.selectExpression.booleanExpression.*;

/**
 * StringExpression is any expression that evaluates to a String. For example:
 * select "string", concat("hello","world") etc...
 * important note: "string" + "string" does not concat in mysql, use the concat() function instead.
 */
@Immutable
public interface StringExpression extends OrderedExpression {

  default BooleanExpression isEqualTo(StringExpression strExp) {
    return null;
  }

  default BooleanExpression isNotEqualTo(StringExpression strExp) {
    return null;
  }

  default BooleanExpression isGreaterThan(StringExpression strExp) {
    return null;
  }

  default BooleanExpression isGreaterThanOrEqual(StringExpression strExp) {
    return null;
  }

  default BooleanExpression isLessThan(StringExpression strExp) {
    return null;
  }

  default BooleanExpression isLessThanOrEqual(StringExpression strExp) {
    return null;
  }

  default BooleanExpression isLike(LiteralString s) {
    return Like.make(this, s);
  }

  default BooleanExpression isNotLike(LiteralString s) {
    return NotLike.make(this, s);
  }

  default BooleanExpression isRegexp(LiteralString regex) {
    return Regexp.make(this, regex);
  }

  default BooleanExpression isNotRegexp(LiteralString regex) {
    return NotRegexp.make(this, regex);
  }

  default BooleanExpression match(LiteralString s) {
    return Match.make(this, s);
  }

  default BooleanExpression eq(StringExpression strExp) {
    // TODO: Each of these shortcut convenience methods should point to the real ones, so code isn't duplicated.
    return null;
  }

  default BooleanExpression eq(Enum<?> item) {
    return Equals.make(this, Literal.make(item.toString()));
  }

  default BooleanExpression ne(StringExpression strExp) {
    return null;
  }

  default BooleanExpression gt(StringExpression strExp) {
    return null;
  }

  default BooleanExpression ge(StringExpression strExp) {
    return null;
  }

  default BooleanExpression lt(StringExpression strExp) {
    return null;
  }

  default BooleanExpression le(StringExpression strExp) {
    return null;
  }

}
