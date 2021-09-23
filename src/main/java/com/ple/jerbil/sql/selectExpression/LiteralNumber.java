package com.ple.jerbil.sql.selectExpression;

/**
 * LiteralNumber looks like this in a normal query:
 * select 5, 32, -3;
 * Contrast that with selecting Numeric columns:
 * select price, amount from tablename;
 */
public class LiteralNumber extends NumericExpression {

    protected LiteralNumber(int value) {
        super(value);
    }
}
