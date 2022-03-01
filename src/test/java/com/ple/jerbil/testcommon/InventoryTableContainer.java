package com.ple.jerbil.testcommon;

import com.ple.jerbil.data.Database;
import com.ple.jerbil.data.GenericInterfaces.Immutable;
import com.ple.jerbil.data.Index;
import com.ple.jerbil.data.query.Table;
import com.ple.jerbil.data.query.TableContainer;
import com.ple.jerbil.data.selectExpression.Column;
import com.ple.jerbil.data.selectExpression.NumericExpression.NumericColumn;
import com.ple.util.IArrayMap;
import com.ple.util.IList;
import com.ple.util.IMap;
import org.jetbrains.annotations.Nullable;

@Immutable
public class InventoryTableContainer extends TableContainer {
  public final NumericColumn playerId;
  public final NumericColumn itemId;
  public final String tableName;

  protected InventoryTableContainer(Table table, IMap<String, Column> columns, NumericColumn playerId, NumericColumn itemId,
                                    IMap<String, Index> indexes, @Nullable NumericColumn autoIncrementColumn) {
    super(table, columns, null, indexes, autoIncrementColumn);
    this.playerId = playerId;
    this.itemId = itemId;
    this.tableName = table.tableName;
  }

  public static InventoryTableContainer make(Database db) {
    final Table inventoryTable = Table.make("inventory", db);
    final NumericColumn playerId = Column.make("playerId", inventoryTable).asInt().primary();
    final NumericColumn itemId = Column.make("itemId", inventoryTable).asInt().primary();
//    final IList<Index> indexSpecs = IArrayList.make(Index.make(IndexType.primary, playerId, itemId));
    final IMap<String, Column> columns = IArrayMap.make(playerId.columnName, playerId, itemId.columnName, itemId);
    return new InventoryTableContainer(inventoryTable, columns, playerId, itemId, null, null);
  }
}
