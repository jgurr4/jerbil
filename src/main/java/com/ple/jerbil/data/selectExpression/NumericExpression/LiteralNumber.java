package com.ple.jerbil.data.selectExpression.NumericExpression;

import com.ple.jerbil.data.selectExpression.Expression;
import com.ple.jerbil.data.selectExpression.Literal;
import com.ple.jerbil.data.selectExpression.booleanExpression.BooleanExpression;
import com.ple.jerbil.data.selectExpression.booleanExpression.GreaterThan;

/**
 * LiteralNumber looks like this in a normal query:
 * select 5, 32, -3;
 * Contrast that with selecting Numeric columns:
 * select price, amount from tablename;
 */
public class LiteralNumber implements Literal, NumericExpression {

    public Integer value;

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

}
