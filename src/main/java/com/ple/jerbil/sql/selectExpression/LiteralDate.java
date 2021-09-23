package com.ple.jerbil.sql.selectExpression;

import com.ple.jerbil.sql.Immutable;

import java.time.LocalDateTime;

/**
 * LiteralDate looks like this in a normal query:
 * select '12-05-1994 01:11:14';
 * Contrast that with selecting Temporal columns:
 * select creation_date from tablename;
 */
@Immutable
public class LiteralDate extends DateExpression {

    protected LiteralDate(LocalDateTime dateTime) {
        super(dateTime);
    }
}
