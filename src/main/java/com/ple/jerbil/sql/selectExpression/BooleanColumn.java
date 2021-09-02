package com.ple.jerbil.sql.selectExpression;

import com.ple.jerbil.sql.selectExpression.booleanExpression.BooleanExpression;

/**
 * BooleanColumn is for compile-time checking to ensure people use a BooleanColumn in the
 * rare cases where a booleanExpression is a Boolean Column. For example: Select * from table where isTrue; // isTrue could be a column with boolean values.
 */
public class BooleanColumn extends BooleanExpression {

}
