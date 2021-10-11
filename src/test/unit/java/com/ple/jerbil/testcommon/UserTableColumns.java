package com.ple.jerbil.testcommon;

import com.ple.jerbil.sql.Immutable;
import com.ple.jerbil.sql.selectExpression.Column;
import com.ple.jerbil.sql.fromExpression.Table;
import com.ple.jerbil.sql.selectExpression.NumericColumn;
import com.ple.jerbil.sql.selectExpression.StringColumn;

@Immutable
public class UserTableColumns {

  public final NumericColumn userId;
  public final StringColumn name;

  public UserTableColumns(Table table) {
    userId = Column.make("userId", table).asInt().primary();
    name = Column.make("name", table).asVarchar().indexed();
  }
}
/* Alternative style that we may decide to support as well
  public final Column userId;
  public final StringColumn name;
    userId = NumericColumn.make("userId", table, false, true);
    name = StringColumn.make("name", table, true, false);
*/
