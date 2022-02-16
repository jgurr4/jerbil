package com.ple.jerbil.testcommon;

import com.ple.jerbil.data.selectExpression.NumericExpression.NumericColumn;
import com.ple.jerbil.data.selectExpression.StringColumn;

public class PlayerTableColumns {
  public final NumericColumn playerId;
  public final NumericColumn userId;
  public final StringColumn name;

  public PlayerTableColumns(NumericColumn playerId, NumericColumn userId, StringColumn name) {
    this.playerId = playerId;
    this.userId = userId;
    this.name = name;
  }
}
