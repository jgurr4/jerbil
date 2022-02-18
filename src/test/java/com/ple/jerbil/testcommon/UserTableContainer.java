package com.ple.jerbil.testcommon;

import com.ple.jerbil.data.Database;
import com.ple.jerbil.data.Immutable;
import com.ple.jerbil.data.Index;
import com.ple.jerbil.data.IndexType;
import com.ple.jerbil.data.query.Table;
import com.ple.jerbil.data.query.TableContainer;
import com.ple.jerbil.data.selectExpression.Column;
import com.ple.jerbil.data.selectExpression.NumericExpression.NumericColumn;
import com.ple.jerbil.data.selectExpression.StringColumn;
import com.ple.util.IArrayList;
import com.ple.util.IArrayMap;
import com.ple.util.IList;

@Immutable
public class UserTableContainer extends TableContainer {
  public final NumericColumn userId;
  public final StringColumn name;
  public final NumericColumn age;
  public final String tableName;

  public UserTableContainer(Table table, NumericColumn userId,
                            StringColumn name, NumericColumn age) {
    super(table, IArrayMap.make(userId.columnName, userId, name.columnName, name, age.columnName, age),
        null, null, null);
    this.userId = userId;
    this.name = name;
    this.age = age;
    this.tableName = table.tableName;
  }

  public static UserTableContainer make(Database db) {
    final Table userTable = Table.make("user", db);
    final NumericColumn userId = Column.make("userId", userTable).bigId();
    final StringColumn name = Column.make("name", userTable).asVarchar().indexed();
    final NumericColumn age = Column.make("age", userTable).asInt();
//    final IList<Index> indexSpecs = IArrayList.make(Index.make(IndexType.secondary, name));
//    final NumericColumn autoIncrementColumn = userId;
    return new UserTableContainer(userTable, userId, name, age);
  }
/* Alternative style that we may decide to support as well
  public final Column userId;
  public final StringColumn name;
    userId = NumericColumn.make("userId", table, false, true);
    name = StringColumn.make("name", table, true, false);
*/

}
