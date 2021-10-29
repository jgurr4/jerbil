package com.ple.jerbil.data.selectExpression;

import com.ple.jerbil.data.Immutable;

import java.time.LocalDateTime;

/**
 * LiteralDate looks like this in a normal query:
 * select '12-05-1994 01:11:14';
 * Contrast that with selecting Temporal columns:
 * select creation_date from tablename;
 */
@Immutable
public class LiteralDate extends DateExpression {

    public final LocalDateTime dateTime;

    protected LiteralDate(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }
}
