package com.ple.jerbil.data.selectExpression.NumericExpression;

import com.ple.jerbil.data.Immutable;
import com.ple.jerbil.data.selectExpression.Expression;
import com.ple.jerbil.data.selectExpression.Literal;
import com.ple.jerbil.data.selectExpression.booleanExpression.BooleanExpression;
import com.ple.jerbil.data.selectExpression.booleanExpression.GreaterThan;

import java.util.Objects;

/**
 * LiteralNumber looks like this in a normal query:
 * select 5, 32, -3;
 * Contrast that with selecting Numeric columns:
 * select price, amount from tablename;
 */
@Immutable
public class LiteralNumber implements Literal, NumericExpression {

    public final Integer value;

    protected LiteralNumber(Integer value) {
        this.value = value;
    }

    public static LiteralNumber make(Integer value) {
        return new LiteralNumber(value);
    }

  @Override
  public GreaterThan isGreaterThan(Expression i) {
    return null;
  }

  @Override
  public BooleanExpression isLessThan(Expression i) {
    return null;
  }

  @Override
  public BooleanExpression eq(Expression item) {
      return null;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof LiteralNumber)) return false;
    LiteralNumber that = (LiteralNumber) o;
    return value.equals(that.value);
  }

  @Override
  public int hashCode() {
    return Objects.hash(value);
  }

  @Override
  public String toString() {
    return "LiteralNumber{" +
      "value=" + value +
      '}';
  }

}
