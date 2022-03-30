package com.ple.jerbil.testcommon;

import com.ple.jerbil.data.*;
import com.ple.util.Immutable;
import com.ple.jerbil.data.query.Table;
import com.ple.jerbil.data.query.TableContainer;
import com.ple.jerbil.data.selectExpression.Column;
import com.ple.jerbil.data.selectExpression.NumericExpression.NumericColumn;
import com.ple.util.IArrayMap;
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
    final NumericColumn playerId = NumericColumn.make("playerId", inventoryTable, DataSpec.make(DataType.integer));
    final NumericColumn itemId = NumericColumn.make("itemId", inventoryTable, DataSpec.make(DataType.integer));
    final IMap<String, Index> indexSpecs = IArrayMap.make("primary", Index.make(IndexType.primary, "primary", inventoryTable, playerId, itemId));
    final IMap<String, Column> columns = IArrayMap.make(playerId.columnName, playerId, itemId.columnName, itemId);
    return new InventoryTableContainer(inventoryTable, columns, playerId, itemId, indexSpecs, null);
  }
}
