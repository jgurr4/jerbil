package com.ple.jerbil.testcommon;

import com.ple.jerbil.data.Database;
import com.ple.jerbil.data.Immutable;
import com.ple.jerbil.data.StorageEngine;
import com.ple.jerbil.data.query.Table;
import com.ple.jerbil.data.query.TableContainer;
import com.ple.jerbil.data.selectExpression.Column;
import com.ple.jerbil.data.selectExpression.NumericExpression.NumericColumn;
import com.ple.jerbil.data.selectExpression.StringColumn;
import com.ple.util.IArrayMap;

@Immutable
public class PlayerTable extends TableContainer {
  public final NumericColumn playerId;
  public final NumericColumn userId;
  public final StringColumn name;
  public final String tableName;

  public PlayerTable(Table table, NumericColumn playerId, NumericColumn userId,
                     StringColumn name) {
    super(table, IArrayMap.make(playerId.columnName, playerId, userId.columnName, userId), StorageEngine.transactional);
    this.playerId = playerId;
    this.userId = userId;
    this.tableName = table.tableName;
    this.name = name;
  }

  public static PlayerTable make(Database db) {
    Table playerTable = Table.make("player", db);
    final NumericColumn playerId = Column.make("playerId", playerTable).id();
    final NumericColumn userId = Column.make("userId", playerTable).asInt();
    final StringColumn name = Column.make("name", playerTable).asVarchar(20);
    return new PlayerTable(playerTable, playerId, userId, name);
  }

}
