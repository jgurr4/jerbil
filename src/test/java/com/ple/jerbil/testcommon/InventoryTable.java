package com.ple.jerbil.testcommon;

import com.ple.jerbil.data.Database;
import com.ple.jerbil.data.Immutable;
import com.ple.jerbil.data.query.Table;
import com.ple.jerbil.data.query.TableContainer;
import com.ple.jerbil.data.selectExpression.Column;
import com.ple.jerbil.data.selectExpression.NumericExpression.NumericColumn;
import com.ple.util.IArrayList;
import com.ple.util.IArrayMap;
import com.ple.util.IList;
import com.ple.util.IMap;

@Immutable
public class InventoryTable extends TableContainer {
  public final NumericColumn playerId;
  public final NumericColumn itemId;
  public final String tableName;

  protected InventoryTable(Table table, NumericColumn playerId, NumericColumn itemId) {
    super(table, IArrayMap.make(playerId.columnName, playerId, itemId.columnName, itemId));
    this.playerId = playerId;
    this.itemId = itemId;
    this.tableName = table.tableName;
  }

  public static InventoryTable make(Database db) {
    final Table inventoryTable = Table.make("inventory", db);
    final NumericColumn playerId = Column.make("playerId", inventoryTable).asInt().primary();
    final NumericColumn itemId = Column.make("itemId", inventoryTable).asInt().primary();
    return new InventoryTable(inventoryTable, playerId, itemId);
  }
}
