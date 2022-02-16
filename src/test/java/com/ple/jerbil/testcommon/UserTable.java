package com.ple.jerbil.testcommon;

import com.ple.jerbil.data.Database;
import com.ple.jerbil.data.Immutable;
import com.ple.jerbil.data.query.Table;
import com.ple.jerbil.data.selectExpression.Column;
import com.ple.jerbil.data.selectExpression.NumericExpression.NumericColumn;
import com.ple.jerbil.data.selectExpression.StringColumn;
import com.ple.util.IArrayList;
import com.ple.util.IList;

@Immutable
public class UserTable extends Table {
  public final NumericColumn userId;
  public final StringColumn name;
  public final NumericColumn age;
  public final UserTableColumns columns;

  public UserTable(Database db) {
    super("user", db);
    userId = Column.make("userId", this).id();
    name = Column.make("name", this).asVarchar().indexed();
    age = Column.make("age", this).asInt();
    columns = new UserTableColumns(userId, name, age);
  }
/* Alternative style that we may decide to support as well
  public final Column userId;
  public final StringColumn name;
    userId = NumericColumn.make("userId", table, false, true);
    name = StringColumn.make("name", table, true, false);
*/

}
