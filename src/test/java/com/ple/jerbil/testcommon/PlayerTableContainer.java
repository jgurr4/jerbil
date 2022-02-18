package com.ple.jerbil.testcommon;

import com.ple.jerbil.data.*;
import com.ple.jerbil.data.query.Table;
import com.ple.jerbil.data.query.TableContainer;
import com.ple.jerbil.data.selectExpression.Column;
import com.ple.jerbil.data.selectExpression.NumericExpression.NumericColumn;
import com.ple.jerbil.data.selectExpression.StringColumn;
import com.ple.util.IArrayList;
import com.ple.util.IArrayMap;
import com.ple.util.IList;

@Immutable
public class PlayerTableContainer extends TableContainer {
  public final NumericColumn playerId;
  public final NumericColumn userId;
  public final StringColumn name;
  public final String tableName;

  public PlayerTableContainer(Table table, NumericColumn playerId, NumericColumn userId,
                              StringColumn name, IList<IndexSpec> indexSpecs,
                              NumericColumn autoIncrementColumn) {
    super(table, IArrayMap.make(playerId.columnName, playerId, userId.columnName, userId), StorageEngine.transactional,
        indexSpecs, autoIncrementColumn);
    this.playerId = playerId;
    this.userId = userId;
    this.tableName = table.tableName;
    this.name = name;
  }

  public static PlayerTableContainer make(Database db) {
    Table playerTable = Table.make("player", db);
    final NumericColumn playerId = Column.make("playerId", playerTable).asInt();
    final NumericColumn userId = Column.make("userId", playerTable).asInt();
    final StringColumn name = Column.make("name", playerTable).asVarchar(20);
    final IList<IndexSpec> indexSpecs = IArrayList.make(IndexSpec.make(IndexType.primary, playerId));
    final NumericColumn autoIncrementColumn = playerId;
    return new PlayerTableContainer(playerTable, playerId, userId, name, indexSpecs, autoIncrementColumn);
  }

}
