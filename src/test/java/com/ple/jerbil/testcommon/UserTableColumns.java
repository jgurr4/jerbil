package com.ple.jerbil.testcommon;

import com.ple.jerbil.data.query.Table;
import com.ple.jerbil.data.query.TableContainer;
import com.ple.jerbil.data.selectExpression.Column;
import com.ple.jerbil.data.selectExpression.NumericExpression.NumericColumn;
import com.ple.jerbil.data.selectExpression.StringColumn;
import com.ple.util.IArrayList;
import com.ple.util.IList;
import com.ple.util.Immutable;

@Immutable
public class UserTableColumns {
  public final NumericColumn userId;
  public final StringColumn name;
  public final NumericColumn age;
  public final IList<Column> columns;
  public final TableContainer tableContainer;

  public UserTableColumns(Table table) {
    userId = Column.make("userId", table).id();
    name = Column.make("name", table).asVarchar().indexed();
    age = Column.make("age", table).asInt();
    columns = IArrayList.make(userId, name, age);
    tableContainer = TableContainer.make(table, columns);
  }
/* Alternative style that we may decide to support as well
  public final Column userId;
  public final StringColumn name;
    userId = NumericColumn.make("userId", table, false, true);
    name = StringColumn.make("name", table, true, false);
*/
}
