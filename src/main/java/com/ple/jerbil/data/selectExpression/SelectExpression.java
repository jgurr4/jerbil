package com.ple.jerbil.data.selectExpression;

import com.ple.jerbil.data.Immutable;
import com.ple.jerbil.data.query.SelectQuery;
import com.ple.util.IArrayList;

/**
 * SelectExpressions take many forms in sql. They can be included in various places in the statement. Such as following
 * the select, where, having, group by clause etc... Expressions can be columns, boolean expressions, numeric expressions, Literals, subqueries etc...
 * see: https://dev.mysql.com/doc/refman/8.0/en/expressions.html
 * SelectExpressions include normal expressions as well as aliased expressions which is why it is
 * inherited by Expressions and AliasedExpressions. Any methods which occur in both Expressions
 * and AliasedExpressions should be put in here.
 */
@Immutable
public interface SelectExpression {

  SelectExpression selectAll = SelectAllExpression.make();

  default SelectQuery select() {
    return SelectQuery.make(IArrayList.make(this));
  }
}
