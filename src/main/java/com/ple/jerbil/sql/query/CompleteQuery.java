package com.ple.jerbil.sql.query;

import com.ple.jerbil.sql.Global;
import com.ple.jerbil.sql.Immutable;
import com.ple.jerbil.sql.SqlGenerator;
import com.ple.jerbil.sql.selectExpression.Literal;
import com.ple.jerbil.sql.selectExpression.SelectExpression;
import com.ple.jerbil.sql.selectExpression.booleanExpression.BooleanExpression;
import com.ple.jerbil.sql.selectExpression.Column;
import com.ple.jerbil.sql.selectExpression.Expression;
import com.ple.util.IHashMap;
import com.ple.util.IList;
import com.ple.util.IMap;
import org.jetbrains.annotations.Nullable;

/**
 * CompleteQuery represents any query that can be considered a full query without any additional clauses or values added.
 * Here is a list of common queries and when they would be considered complete:
 * For select to be considered complete make sure values and/or columns from table are plugged in.
 * select 2 + 2;   //select 3 also works.
 * select name from user;
 *
 * For update to be considered complete a table and set clause must be specified:
 * update user set name = 'bonny';  //Note that using update without where clause will update every record, so only do
 * this if you want to replace every row's value for a specific column.
 *
 * For delete to be considered complete the minimum requirements are that a table is specified.
 * delete from user;  //Note that delete without where clause will delete every record. So always use where clause unless
 * you want to erase your entire table.
 *
 * For insert to be considered complete, the minimum requirements are that a table, and the values are specified.
 * insert into user values (default, 'bonny', null, '10-13-22', 5);
 */
@Immutable
public class CompleteQuery extends Query {

  protected CompleteQuery(@Nullable IList<BooleanExpression> where, @Nullable FromExpression fromExpression, @Nullable QueryType queryType, @Nullable IList<SelectExpression> select, @Nullable IList<SelectExpression> groupBy, @Nullable IList<SelectExpression> orderBy, @Nullable IList<BooleanExpression> having, @Nullable Limit limit, @Nullable IMap<Column, Expression> set, @Nullable boolean mayInsert, @Nullable boolean mayReplace, @Nullable boolean triggerDeleteWhenReplacing, @Nullable boolean mayThrowOnDuplicate) {
    super(where, fromExpression, queryType, select, groupBy, orderBy, having, limit, set, mayInsert, mayReplace, triggerDeleteWhenReplacing, mayThrowOnDuplicate);
  }

  public static CompleteQuery make(IMap<Column, Expression> set) {
    return new CompleteQuery(null, null, null, null, null, null, null, null, set, false, false, false, false);
  }

  public String toSql() {
    if (Global.sqlGenerator == null) {
      throw new NullPointerException("Global.sqlGenerator not set.");
    }
    return toSql(Global.sqlGenerator);
  }

  public String toSql(SqlGenerator generator) {
    return null;
  }

  public CompleteQuery where(BooleanExpression condition) {
    return null;
  }

  public CompleteQuery and(BooleanExpression expression) {
    return null;
  }

  public CompleteQuery groupBy(Column name) {
    return null;
  }

  public Expression minus(int i) {
    return null;
  }


  public CompleteQuery set(Column column, Literal value) {
    return null;
  }

}
