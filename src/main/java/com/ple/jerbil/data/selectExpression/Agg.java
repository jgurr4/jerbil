package com.ple.jerbil.data.selectExpression;

/**
 * Agg is short for Aggregate, it's a holder for all the Singleton objects which are created
 * for each query. It is used for all aggregate functions in mysql like COUNT, SUM, MIN, MAX etc...
 * They can be used in Select Expression and Having clause.
 * count, max, min, sum etc... will all be their own classes.
 * count and sum will extend NumericExpression. others will extend others.
 */
public class Agg {

  public static CountAgg count;

}
