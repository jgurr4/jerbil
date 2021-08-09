package com.ple.jerbil.testcommon;

import com.ple.jerbil.sql.expression.Column;
import com.ple.jerbil.sql.StorageEngine;
import com.ple.jerbil.sql.fromExpression.Table;

public class PlayerTable extends Table {

  final Column playerId = Column.make("playerId", this).primary();
  final Column userId = Column.make("userId", this).id();
  public final Column name = Column.make("name", this).varchar(20);

  public PlayerTable() {
    super("player");
    engine = StorageEngine.transactional;
  }

}
