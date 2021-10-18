package com.ple.jerbil.sql;

import com.ple.jerbil.sql.query.CompleteQuery;
import com.ple.jerbil.sql.selectExpression.Column;

public class PotentialQuery {
    public CompleteQuery select(Column column) {
        return CompleteQuery.make();
    }
}
