package com.ple.jerbil.testcommon;

import com.ple.jerbil.sql.Immutable;
import com.ple.jerbil.sql.expression.Column;
import com.ple.jerbil.sql.fromExpression.Table;

@Immutable
public class InventoryTableColumns {

  public final Column playerId;
  public final Column itemId;

  public InventoryTableColumns(Table table) {
    playerId = Column.make("playerId", table).primary();
    itemId = Column.make("itemId", table).primary();
  }

}
