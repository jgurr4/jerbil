package com.ple.jerbil.testcommon;

import com.ple.jerbil.sql.expression.Column;
import com.ple.jerbil.sql.fromExpression.Table;

public class InventoryTable extends Table {

  final Column playerId = Column.make("playerId", this).primary();
  final Column itemId = Column.make("itemId", this).primary();

  public InventoryTable() {
    super("item");
  }

}
