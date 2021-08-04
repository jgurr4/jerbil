package com.ple.jerbil.testcommon;

import com.ple.jerbil.sql.expression.Column;
import com.ple.jerbil.sql.fromExpression.Table;

public class InventoryTable extends Table {

  final Column playerId = Column.make("playerId", Column.name, Column.table, Column.dataSpec, Column.indexed, Column.primary).primary();
  final Column itemId = Column.make("itemId", Column.name, Column.table, Column.dataSpec, Column.indexed, Column.primary).primary();

  public InventoryTable() {
    super("item");
  }

}
