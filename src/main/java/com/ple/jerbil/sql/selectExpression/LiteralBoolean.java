package com.ple.jerbil.sql.selectExpression;

import com.ple.jerbil.sql.selectExpression.booleanExpression.BooleanExpression;

/**
 * LiteralBoolean looks like this in a normal query:
 * select true, false;
 * Contrast that with selecting boolean columns:
 * select isTrue, isFalse from tablename;
 */
public class LiteralBoolean extends BooleanExpression {

}
