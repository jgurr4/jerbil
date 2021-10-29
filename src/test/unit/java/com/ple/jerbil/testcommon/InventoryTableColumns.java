package com.ple.jerbil.testcommon;

import com.ple.jerbil.data.Immutable;
import com.ple.jerbil.data.selectExpression.Column;
import com.ple.jerbil.data.query.Table;
import com.ple.jerbil.data.selectExpression.NumericColumn;

@Immutable
public class InventoryTableColumns {

  public final NumericColumn playerId;
  public final NumericColumn itemId;

  public InventoryTableColumns(Table table) {
    playerId = Column.make("playerId", table).primary();
    itemId = Column.make("itemId", table).primary();
  }

}
