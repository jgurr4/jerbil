package com.ple.jerbil.data.selectExpression;

import com.ple.jerbil.data.selectExpression.booleanExpression.BooleanExpression;

import java.util.Objects;

/**
 * LiteralString looks like this in a normal query:
 * select "john";
 * Contrast that with selecting String columns:
 * select first_name from tablename;
 */
public class LiteralString implements Literal, StringExpression {

    public String value;

    protected LiteralString(String value) {
        this.value = value;
    }

    public static LiteralString make(String value) {
        return new LiteralString(value);
    }

    @Override
    public BooleanExpression isGreaterThan(Expression i) {
        return null;
    }

    @Override
    public BooleanExpression isLessThan(Expression i) {
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LiteralString)) return false;
        LiteralString that = (LiteralString) o;
        return value.equals(that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return "LiteralString{" +
          "value='" + value + '\'' +
          '}';
    }

}
