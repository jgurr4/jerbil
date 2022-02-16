package com.ple.jerbil.testcommon;

import com.ple.jerbil.data.Database;
import com.ple.jerbil.data.Immutable;
import com.ple.jerbil.data.query.Table;
import com.ple.jerbil.data.selectExpression.Column;
import com.ple.jerbil.data.selectExpression.NumericExpression.NumericColumn;
import com.ple.util.IArrayList;
import com.ple.util.IList;

@Immutable
public class InventoryTable extends Table {
  public final NumericColumn playerId;
  public final NumericColumn itemId;
  public final InventoryTableColumns columns;

  public InventoryTable(Database db) {
    super("inventory", db);
    playerId = Column.make("playerId", this).asInt().primary();
    itemId = Column.make("itemId", this).asInt().primary();
    columns = new InventoryTableColumns(playerId, itemId);
  }
}
