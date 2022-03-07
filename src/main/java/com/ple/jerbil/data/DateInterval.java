package com.ple.jerbil.data;

import com.ple.util.Immutable;

/**
 * The purpose of DateInterval is for RDBMs which support using interval syntax for operations on temporal data types.
 * For example:
 * select now() + interval 1 hour;
 */
@Immutable
public class DateInterval {

    public final int year;
    public final int month;
    public final int day;
    public final int hour;
    public final int minute;
    public final int second;

    protected DateInterval(int year, int month, int day, int hour, int minute, int second) {
        this.year = year;
        this.month = month;
        this.day = day;
        this.hour = hour;
        this.minute = minute;
        this.second = second;
    }

    public static DateInterval makeDay(int i) {
        return null;
    }

    public static DateInterval makeHour(int i) {
        return null;
    }
}
