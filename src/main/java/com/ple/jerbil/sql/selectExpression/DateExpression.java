package com.ple.jerbil.sql.selectExpression;

/**
 * DateExpression represents any date objects being used in expressions. Because you cannot compare a date object with
 * another data type without some intermediate functions this class is meant to avoid letting the user accidentally compare
 * a date with a number or something. For example: select 05-23-1990 + 5; OR select * from table where date > true;
 */
public class DateExpression {

}
