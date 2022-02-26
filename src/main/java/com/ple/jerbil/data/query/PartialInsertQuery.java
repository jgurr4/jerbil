package com.ple.jerbil.data.query;

import com.ple.jerbil.data.Order;
import com.ple.jerbil.data.selectExpression.Column;
import com.ple.jerbil.data.selectExpression.Expression;
import com.ple.jerbil.data.selectExpression.Literal;
import com.ple.jerbil.data.selectExpression.SelectExpression;
import com.ple.jerbil.data.selectExpression.booleanExpression.BooleanExpression;
import com.ple.util.*;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class PartialInsertQuery extends PartialQueryWithValues {

  protected PartialInsertQuery(@Nullable BooleanExpression where, @Nullable FromExpression fromExpression,
                               @Nullable QueryType queryType, @Nullable IList<SelectExpression> select,
                               @Nullable IList<SelectExpression> groupBy, @Nullable IMap<SelectExpression, Order> orderBy,
                               @Nullable BooleanExpression having, @Nullable Limit limit,
                               @Nullable IList<IMap<Column, Expression>> set, @Nullable QueryFlags queryFlags,
                               @Nullable Union union) {
    super(where, fromExpression, queryType, select, groupBy, orderBy, having, limit, set, queryFlags, union);
  }

  public static PartialInsertQuery make(TableContainer table) {
    return new PartialInsertQuery(null, table, QueryType.insert, null, null, null, null,
        null, null, null, null);
  }

  public static PartialInsertQuery make(TableContainer tableContainer, QueryFlags queryFlags) {
    return new PartialInsertQuery(null, tableContainer, QueryType.insert, null, null, null,
        null, null, null, queryFlags, null);
  }

  public InsertQuery set(List<Column> columns, List<List<String>> values) {
    IList<IMap<Column, Expression>> records = IArrayList.make();
    for (int i = 0; i < values.size(); i++) {
      for (int j = 0; j < columns.size(); j++) {
        if (!(i >= records.toArray().length)) {
          records.toArray()[i] = records.toArray()[i].put(columns.get(j), Literal.make(values.get(i).get(j)));
        } else {
          final IMap<Column, Expression> record = IHashMap.make(columns.get(j), Literal.make(values.get(i).get(j)));
          records = records.add(record);
        }
      }
    }
    return InsertQuery.make(records, fromExpression);
  }

  public InsertQuery set(Column column, Literal value) {
    return InsertQuery.make(IArrayList.make(IArrayMap.make(column, value)), fromExpression);
  }

}
