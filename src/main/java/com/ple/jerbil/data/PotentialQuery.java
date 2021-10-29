package com.ple.jerbil.data;

import com.ple.jerbil.data.query.SelectQuery;
import com.ple.jerbil.data.selectExpression.SelectExpression;

/**
 * Potential Query represents any type of query, but adds the important 'select' method.
 * The 'select' method is the only method which can turn any incomplete or partialQuery into a Complete Query at any
 * point in the query building process. This is because select('values') on its own is considered a CompleteQuery
 * without any additional details/methods/clauses.
 * For example:
 * `user.where(userColumns.name.eq("john"))` is not a CompleteQuery on its own,
 * but is able to be appended with `.select(userColumns.userId);` which turns it into a CompleteQuery of type SelectQuery.
 *
 * All subclasses are allowed to implement select method in the way which suits it best.
 */
@Immutable
public abstract class PotentialQuery {
    abstract public SelectQuery select(SelectExpression... selectExpressions);
}
