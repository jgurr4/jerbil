package com.ple.jerbil.data.selectExpression;

/**
 * LiteralString looks like this in a normal query:
 * select "john";
 * Contrast that with selecting String columns:
 * select first_name from tablename;
 */
public class LiteralString extends StringExpression {

    public String value;

    protected LiteralString(String value) {
        this.value = value;
    }

    public static LiteralString make(String value) {
        return new LiteralString(value);
    }
}
