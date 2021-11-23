package com.ple.jerbil.data.selectExpression;

import com.ple.jerbil.data.selectExpression.booleanExpression.BooleanExpression;

/**
 * LiteralNumber looks like this in a normal query:
 * select 5, 32, -3;
 * Contrast that with selecting Numeric columns:
 * select price, amount from tablename;
 */
public class LiteralNumber extends NumericExpression implements Literal {

    public Integer value;

    protected LiteralNumber(Integer value) {
        this.value = value;
    }

    public static LiteralNumber make(Integer value) {
        return new LiteralNumber(value);
    }

  @Override
  public BooleanExpression eq(Expression item) {
      return null;
  }

}
