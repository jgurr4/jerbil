package com.ple.jerbil.testcommon;

import com.ple.jerbil.data.*;
import com.ple.util.*;
import com.ple.jerbil.data.query.Table;
import com.ple.jerbil.data.query.TableContainer;
import com.ple.jerbil.data.selectExpression.Column;
import com.ple.jerbil.data.selectExpression.NumericExpression.NumericColumn;
import com.ple.jerbil.data.selectExpression.StringColumn;
import org.jetbrains.annotations.Nullable;

@Immutable
public class UserTableContainer extends TableContainer {
  public final NumericColumn userId;
  public final StringColumn name;
  public final NumericColumn age;
  public final String tableName;

  protected UserTableContainer(Table table, IMap<String, Column> columns, NumericColumn userId,
                               StringColumn name, NumericColumn age, IMap<String, Index> indexes,
                               @Nullable NumericColumn autoIncrementColumn) {
    super(table, columns, null, indexes, autoIncrementColumn);
    this.userId = userId;
    this.name = name;
    this.age = age;
    this.tableName = table.tableName;
  }

  public static UserTableContainer make(Database db) {
    final Table userTable = Table.make("user", db);
    //TODO: Consider calling BuildingHints something else because that doesn't belong in Columns, You could say ColumnProps or something. Primary/indexes shouldn't be inside it though, primary is inside indexes not in columns.
    final NumericColumn userId = NumericColumn.make("userId", userTable, DataSpec.make(DataType.bigint),
       null, BuildingHints.make().autoInc());
    final StringColumn name = StringColumn.make("name", userTable, DataSpec.make(DataType.varchar));
    final NumericColumn age = NumericColumn.make("age", userTable, DataSpec.make(DataType.integer));
    final String idxName1 = DatabaseService.generateIndexName(name); //Alternatively, users can name the indexes themselves rather than using the generator.
    final String idxName2 = DatabaseService.generateIndexName(userId);
    final IMap<String, Index> indexSpecs = IArrayMap.make(idxName1,
        Index.make(IndexType.secondary, idxName1, userTable, name), idxName2,
        Index.make(IndexType.primary, idxName2, userTable, userId));
    final IMap<String, Column> columns = IArrayMap.make(userId.columnName, userId, name.columnName, name,
        age.columnName, age);
    return new UserTableContainer(userTable, columns, userId, name, age, indexSpecs, userId);
  }
  //TODO: Find a way to make DatabaseBuilderOld generate the DatabaseContainer using this new style instead of old which used buildinghints.

}
