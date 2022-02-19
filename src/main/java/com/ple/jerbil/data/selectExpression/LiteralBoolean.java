package com.ple.jerbil.data.selectExpression;

import com.ple.jerbil.data.selectExpression.booleanExpression.BooleanExpression;

import java.util.Objects;

/**
 * LiteralBoolean looks like this in a normal query:
 * select true, false;
 * Contrast that with selecting boolean columns:
 * select isTrue, isFalse from tablename;
 */
public class LiteralBoolean implements BooleanExpression, Literal {

    public Boolean bool;

    protected LiteralBoolean(Boolean bool) {
        this.bool = bool;
    }

    public LiteralBoolean make(Boolean bool) {
        return new LiteralBoolean(bool);
    }

    public BooleanExpression isGreaterThan(Expression i) {
        return null;
    }

    public BooleanExpression isLessThan(Expression i) {
        return null;
    }

    public BooleanExpression eq(Expression item) {
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LiteralBoolean)) return false;
        LiteralBoolean that = (LiteralBoolean) o;
        return bool.equals(that.bool);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bool);
    }

    @Override
    public String toString() {
        return "LiteralBoolean{" +
          "bool=" + bool +
          '}';
    }

}
