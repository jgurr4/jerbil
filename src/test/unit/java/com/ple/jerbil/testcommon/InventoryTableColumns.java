package com.ple.jerbil.testcommon;

import com.ple.jerbil.sql.Immutable;
import com.ple.jerbil.sql.selectExpression.Column;
import com.ple.jerbil.sql.query.Table;
import com.ple.jerbil.sql.selectExpression.NumericColumn;

@Immutable
public class InventoryTableColumns {

  public final NumericColumn playerId;
  public final NumericColumn itemId;

  public InventoryTableColumns(Table table) {
    playerId = Column.make("playerId", table).primary();
    itemId = Column.make("itemId", table).primary();
  }

}
