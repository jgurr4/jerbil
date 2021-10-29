package com.ple.jerbil.data.selectExpression;

import com.ple.jerbil.data.selectExpression.booleanExpression.BooleanExpression;

/**
 * LiteralBoolean looks like this in a normal query:
 * select true, false;
 * Contrast that with selecting boolean columns:
 * select isTrue, isFalse from tablename;
 */
public class LiteralBoolean extends BooleanExpression {

    public Boolean bool;

    protected LiteralBoolean(Boolean bool) {
        this.bool = bool;
    }

    public LiteralBoolean make(Boolean bool) {
        return new LiteralBoolean(bool);
    }

}
