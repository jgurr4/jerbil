package com.ple.jerbil.data.query;

import com.ple.jerbil.data.*;
import com.ple.jerbil.data.GenericInterfaces.Immutable;
import com.ple.jerbil.data.GenericInterfaces.ReactiveWrapper;
import com.ple.jerbil.data.bridge.DataBridge;
import com.ple.jerbil.data.selectExpression.*;
import com.ple.jerbil.data.selectExpression.booleanExpression.BooleanExpression;
import com.ple.jerbil.data.translator.LanguageGenerator;
import com.ple.util.IList;
import com.ple.util.IMap;
import io.r2dbc.spi.Result;
import org.jetbrains.annotations.Nullable;

/**
 * CompleteQuery represents any query that can be considered a full query without any additional clauses or values added.
 * Here is a list of common queries and when they would be considered complete:
 * For select to be considered complete make sure values and/or columns from table are plugged in.
 * select 2 + 2;   //select 3 also works.
 * select name from user;
 * <p>
 * For update to be considered complete a table and set clause must be specified:
 * update user set name = 'bonny';  //Note that using update without where clause will update every record, so only do
 * this if you want to replace every row's value for a specific column.
 * <p>
 * For delete to be considered complete the minimum requirements are that a table is specified.
 * delete from user;  //Note that delete without where clause will delete every record. So always use where clause unless
 * you want to erase your entire table.
 * <p>
 * For insert to be considered complete, the minimum requirements are that a table, and the values are specified.
 * insert into user values (default, 'bonny', null, '10-13-22', 5);
 */
@Immutable
public class CompleteQuery extends Query {

  protected CompleteQuery(@Nullable BooleanExpression where, @Nullable FromExpression fromExpression,
                          @Nullable QueryType queryType, @Nullable IList<SelectExpression> select,
                          @Nullable IList<SelectExpression> groupBy, @Nullable IMap<SelectExpression, SortOrder> orderBy,
                          @Nullable BooleanExpression having, @Nullable Limit limit,
                          @Nullable IList<IMap<Column, Expression>> set, @Nullable QueryFlags queryFlags,
                          @Nullable Union union) {
    super(where, fromExpression, queryType, select, groupBy, orderBy, having, limit, set, queryFlags, union);
  }

  public String toSql() {
    if (DataGlobal.bridge == null) {
      throw new NullPointerException("Global.sqlGenerator not set.");
    }
    return toSql(DataGlobal.bridge.getGenerator());
  }

  public String toSql(LanguageGenerator generator) {
    return generator.toSql(this);
  }

  public ReactiveWrapper<Result> execute() {
    return execute(DataGlobal.bridge);
  }

  private ReactiveWrapper<Result> execute(DataBridge bridge) {
//    return bridge.execute(toSql());
    return null;
  }

  public CompleteQuery limit(int offset, int limit) {
    if (this instanceof SelectQuery) {
      return SelectQuery.make(where, fromExpression, queryType, select, groupBy, orderBy, having,
          Limit.make(offset, limit), set, queryFlags, union);
    } else if (this instanceof InsertQuery) {
      return InsertQuery.make(where, fromExpression, queryType, select, groupBy, orderBy, having,
          Limit.make(offset, limit), set,
          queryFlags, union);
    } else if (this instanceof UpdateQuery) {
      return UpdateQuery.make(where, fromExpression, queryType, select, groupBy, orderBy, having,
          Limit.make(offset, limit), set,
          queryFlags, union);
    } else if (this instanceof DeleteQuery) {
      return DeleteQuery.make(where, fromExpression, queryType, select, groupBy, orderBy, having,
          Limit.make(offset, limit), set,
          queryFlags, union);
    }
    return null;
  }

  public CompleteQuery limit(int limit) {
    return limit(0, limit);
  }
}
