package com.ple.jerbil.data.selectExpression;

import com.ple.jerbil.data.DateInterval;
import com.ple.jerbil.data.Immutable;
import com.ple.jerbil.data.selectExpression.booleanExpression.BooleanExpression;

import java.time.LocalDateTime;

/**
 * LiteralDate looks like this in a normal query:
 * select '12-05-1994 01:11:14';
 * Contrast that with selecting Temporal columns:
 * select creation_date from tablename;
 */
@Immutable
public class LiteralDate implements Literal, DateExpression {

    public final LocalDateTime dateTime;

    protected LiteralDate(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    @Override
    public DateExpression plus(DateInterval dateInterval) {
        return null;
    }

    @Override
    public DateExpression minus(DateInterval dateInterval) {
        return null;
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
    public BooleanExpression eq(Expression item) {
        return null;
    }

}
