package com.ple.jerbil.sql.selectExpression.booleanExpression;
import com.ple.jerbil.sql.Immutable;
import com.ple.jerbil.sql.selectExpression.Expression;

/**
 * BooleanExpression is any time a boolean data type is used in an expression. For example:
 * select isAEmployee from group; OR select * from table where isAEmployee = true;
 * You should not be able to mix booleans with other data types. For example:
 * select true - 3; // This should not be allowed to compile.
 * For cases where a column is used as a boolean('where isTrue;') see BooleanColumn.
 */
@Immutable
public class BooleanExpression extends Expression {

}


