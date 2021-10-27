package com.ple.jerbil.testcommon;

import com.ple.jerbil.sql.Immutable;
import com.ple.jerbil.sql.selectExpression.Column;
import com.ple.jerbil.sql.query.Table;
import com.ple.jerbil.sql.selectExpression.NumericColumn;
import com.ple.jerbil.sql.selectExpression.StringColumn;

@Immutable
public class PlayerTableColumns {

  public final NumericColumn playerId;
  public final NumericColumn userId;
  public final StringColumn name;

  public PlayerTableColumns(Table table) {
    playerId = Column.make("playerId", table).primary();
    userId = Column.make("userId", table).id();
    name = Column.make("name", table).asVarchar(20);
  }

}
