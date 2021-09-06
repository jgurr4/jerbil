package com.ple.jerbil.sql.selectExpression;

/**
 * LiteralDate looks like this in a normal query:
 * select '12-05-1994 01:11:14';
 * Contrast that with selecting Temporal columns:
 * select creation_date from tablename;
 */
public class LiteralDate implements Literal {

}
