package com.ple.jerbil.testcommon;

import com.ple.jerbil.data.Immutable;
import com.ple.jerbil.data.selectExpression.Column;
import com.ple.jerbil.data.query.Table;
import com.ple.jerbil.data.selectExpression.NumericExpression.NumericColumn;
import com.ple.jerbil.data.selectExpression.StringColumn;

@Immutable
public class PlayerTableColumns {

  public final NumericColumn playerId;
  public final NumericColumn userId;
  public final StringColumn name;

  public PlayerTableColumns(Table table) {
    playerId = Column.make("playerId").id();
    userId = Column.make("userId").asInt();
    name = Column.make("name").asVarchar(20);
    table.add(playerId, userId, name);
  }

}
