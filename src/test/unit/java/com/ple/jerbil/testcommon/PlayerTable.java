package com.ple.jerbil.testcommon;

import com.ple.jerbil.Column;
import com.ple.jerbil.Table;

public class PlayerTable extends Table {

  final Column playerId = Column.make("playerId").primary();
  final Column userId = Column.make("userId").id();
  final Column name = Column.make("name").varchar(20);

  public PlayerTable() {
    super("player");
  }

}
