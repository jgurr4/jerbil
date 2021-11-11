package com.ple.jerbil.data.selectExpression;

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

    public LiteralNumber make(Integer value) {
        return new LiteralNumber(value);
    }
}
