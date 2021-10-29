package com.ple.jerbil.data.selectExpression.booleanExpression;
import com.ple.jerbil.data.Immutable;
import com.ple.jerbil.data.selectExpression.Expression;
import com.ple.jerbil.data.selectExpression.OrderedExpression;

/**
 * BooleanExpression is any expression which evaluates to a boolean data type. For example:
 * select true, false, isAEmployee, isAManager; are BooleanExpressions.
 * They also can be the expressions in the where or having clause. For example:
 * select * from tablename where name = 'bob' OR id between 10 and 20 AND amount > 100;
 * Each expression using =, between, > operators is a BooleanExpression. Many more operators exist for this class, and
 * each one are subclasses of this class.
 *
 * You are not able to mix booleans with some other data types. For example:
 * select true - 3;   // This will not compile.
 * select 1 = 1;  // returns true.
 *
 * For cases where a column is used as a boolean, for example: 'select * from table where isTrue;') see BooleanColumn.
 */
@Immutable
public class BooleanExpression extends OrderedExpression {

    public BooleanExpression() {
    }

    @Override
    public BooleanExpression eq(Expression item) {
        return null;
    }

    public static BooleanExpression make() {
        return new BooleanExpression();
    }

}


