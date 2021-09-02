package com.ple.jerbil.sql.selectExpression;

/**
 * Agg is short for Aggregate, it's a holder for all the Singleton objects which are created
 * for each query. It is used for all aggregate functions in mysql like COUNT, SUM, MIN, MAX etc...
 * They can be used in Select Expression and Having clause.
 */
public class Agg extends Expression {

  public static Agg count;

}
