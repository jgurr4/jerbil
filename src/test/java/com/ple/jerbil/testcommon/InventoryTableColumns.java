package com.ple.jerbil.testcommon;

import com.ple.jerbil.data.selectExpression.NumericExpression.NumericColumn;

public class InventoryTableColumns {
  public final NumericColumn playerId;
  public final NumericColumn itemId;

  public InventoryTableColumns(NumericColumn playerId, NumericColumn itemId) {
    this.playerId = playerId;
    this.itemId = itemId;
  }

}
