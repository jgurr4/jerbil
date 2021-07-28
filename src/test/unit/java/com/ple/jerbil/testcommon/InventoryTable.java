package com.ple.jerbil.testcommon;

import com.ple.jerbil.sql.expression.Column;
import com.ple.jerbil.sql.Table;

public class InventoryTable extends Table {

  final Column playerId = Column.make("playerId").primary();
  final Column itemId = Column.make("itemId").primary();

  public InventoryTable() {
    super("item");
  }

}
