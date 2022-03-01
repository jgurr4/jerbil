package com.ple.jerbil.testcommon;

import com.ple.jerbil.data.*;
import com.ple.jerbil.data.GenericInterfaces.Immutable;
import com.ple.jerbil.data.query.Table;
import com.ple.jerbil.data.query.TableContainer;
import com.ple.jerbil.data.selectExpression.Column;
import com.ple.jerbil.data.selectExpression.NumericExpression.NumericColumn;
import com.ple.jerbil.data.selectExpression.StringColumn;
import com.ple.util.IArrayMap;
import com.ple.util.IList;
import com.ple.util.IMap;
import org.jetbrains.annotations.Nullable;

@Immutable
public class PlayerTableContainer extends TableContainer {
  public final NumericColumn playerId;
  public final NumericColumn userId;
  public final StringColumn name;
  public final String tableName;

  protected PlayerTableContainer(Table table, IMap<String, Column> columns, NumericColumn playerId, NumericColumn userId,
                                 StringColumn name, IMap<String, Index> indexes, @Nullable NumericColumn autoIncrementColumn) {
    super(table, columns, StorageEngine.transactional,
        indexes, autoIncrementColumn);
    this.playerId = playerId;
    this.userId = userId;
    this.tableName = table.tableName;
    this.name = name;
  }

  public static PlayerTableContainer make(Database db) {
    Table playerTable = Table.make("player", db);
    final NumericColumn playerId = Column.make("playerId", playerTable).asInt().ai();
    final NumericColumn userId = Column.make("userId", playerTable).asInt();
    final StringColumn name = Column.make("name", playerTable).asVarchar(20);
//    final IList<Index> indexes = IArrayList.make(Index.make(IndexType.primary, playerId, userId));
//    final NumericColumn autoIncrementColumn = playerId;
    final IMap<String, Column> columns = IArrayMap.make(playerId.columnName, playerId, userId.columnName, userId,
        name.columnName, name);
    return new PlayerTableContainer(playerTable, columns, playerId, userId, name, null, null);
  }

}
