package com.ple.jerbil.testcommon;

import com.ple.jerbil.data.Immutable;
import com.ple.jerbil.data.selectExpression.*;
import com.ple.jerbil.data.query.Table;
import com.ple.jerbil.data.selectExpression.NumericExpression.NumericColumn;

@Immutable
public class UserTableColumns {

  public final NumericColumn userId;
  public final StringColumn name;
  public NumericColumn age;

  public UserTableColumns(Table table) {
    userId = Column.make("userId", table).asInt().primary();
    name = Column.make("name", table).asVarchar().indexed();
    age = Column.make("age", table).asInt();
  }
}
/* Alternative style that we may decide to support as well
  public final Column userId;
  public final StringColumn name;
    userId = NumericColumn.make("userId", table, false, true);
    name = StringColumn.make("name", table, true, false);
*/
