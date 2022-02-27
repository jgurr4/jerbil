package com.ple.jerbil.data.selectExpression;

import com.ple.jerbil.data.DateInterval;
import com.ple.jerbil.data.Immutable;
import com.ple.jerbil.data.selectExpression.booleanExpression.BooleanExpression;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * LiteralDate looks like this in a normal query:
 * select '12-05-1994 01:11:14';
 * Contrast that with selecting Temporal columns:
 * select creation_date from tablename;
 */
@Immutable
public class LiteralDate implements Literal, DateExpression {

    public final LocalDateTime dateTime;
    public static final DateExpression currentTimestamp = CurrentTimestamp.make();

    protected LiteralDate(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public static DateExpression make(LocalDateTime ldateTime) {
        return new LiteralDate(ldateTime);
    }
/*
    @Override
    public DateExpression plus(DateInterval dateInterval) {
        return null;
    }

    @Override
    public DateExpression minus(DateInterval dateInterval) {
        return null;
    }

*/
    public BooleanExpression isGreaterThan(Expression i) {
        return null;
    }

    public BooleanExpression isLessThan(Expression i) {
        return null;
    }

    public BooleanExpression eq(Expression item) {
        return null;
    }

    public BooleanExpression eq(Enum<?> value) {
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LiteralDate)) return false;
        LiteralDate that = (LiteralDate) o;
        return dateTime.equals(that.dateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dateTime);
    }

    @Override
    public String toString() {
        return "LiteralDate{" +
          "dateTime=" + dateTime +
          '}';
    }

}
