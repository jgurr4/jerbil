package com.ple.jerbil.generic;

import com.ple.jerbil.sql.DateInterval;
import com.ple.jerbil.sql.selectExpression.Literal;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.Month;


/**
 * The purpose of genericTests is to test that the api works when paired with the correct translator. So the tests below
 * will test output in several different formats which each use their own translator. There will be a MySQL translator,
 * A javascript translator, and more.
 */
public class genericTests {
    @Test
    void testLiteral() {
        final LocalDateTime dateTime = LocalDateTime.of(2014, Month.DECEMBER, 12, 01, 05, 53);
        Literal.make(dateTime).plus(DateInterval.makeDay(1)).plus(DateInterval.makeHour(1)).minus(DateInterval.makeDay(1));
    }

    @Test
    void testExpression() {
        Literal.make(5).isGreaterThan(Literal.make(3));
        // Should become: select 5 > 3;
    }
}
