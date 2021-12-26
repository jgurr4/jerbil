package com.ple.jerbil.testcommon;

import com.ple.jerbil.data.Immutable;
import com.ple.jerbil.data.selectExpression.Column;
import com.ple.jerbil.data.query.Table;
import com.ple.jerbil.data.selectExpression.NumericExpression.NumericColumn;

@Immutable
public class InventoryTableColumns {

  public final NumericColumn playerId;
  public final NumericColumn itemId;

  public InventoryTableColumns(Table table) {
    playerId = Column.make("playerId", table).asInt().primary();
    itemId = Column.make("itemId", table).asInt().primary();
    table.add(playerId);
    table.add(itemId);
  }

}
