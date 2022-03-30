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
    final NumericColumn playerId = NumericColumn.make("playerId", playerTable, DataSpec.make(DataType.integer), BuildingHints.make().autoInc());
    final NumericColumn userId = NumericColumn.make("userId", playerTable, DataSpec.make(DataType.integer));
    final StringColumn name = StringColumn.make("name", playerTable, DataSpec.make(DataType.varchar, 20));
    final String indexName = DatabaseService.generateIndexName(playerId);
    final IMap<String, Index> indexes = IArrayMap.make(indexName, Index.make(IndexType.primary, indexName, playerTable, playerId));
    final IMap<String, Column> columns = IArrayMap.make(playerId.columnName, playerId, userId.columnName, userId,
        name.columnName, name);
    return new PlayerTableContainer(playerTable, columns, playerId, userId, name, indexes, playerId);
  }

}
