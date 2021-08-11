package com.ple.jerbil.testcommon;

import com.ple.jerbil.sql.Immutable;
import com.ple.jerbil.sql.expression.Column;
import com.ple.jerbil.sql.fromExpression.Table;

@Immutable
public class PlayerTableColumns {

  public final Column playerId;
  public final Column userId;
  public final Column name;

  public PlayerTableColumns(Table table) {
    playerId = Column.make("playerId", table).primary();
    userId = Column.make("userId", table).id();
    name = Column.make("name", table).varchar(20);
  }

}
