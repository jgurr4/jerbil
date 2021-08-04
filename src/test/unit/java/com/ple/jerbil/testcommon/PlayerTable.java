package com.ple.jerbil.testcommon;

import com.ple.jerbil.sql.expression.Column;
import com.ple.jerbil.sql.StorageEngine;
import com.ple.jerbil.sql.fromExpression.Table;

public class PlayerTable extends Table {

  final Column playerId = Column.make("playerId", Column.name, Column.table, Column.dataSpec, Column.indexed, Column.primary).primary();
  final Column userId = Column.make("userId", Column.name, Column.table, Column.dataSpec, Column.indexed, Column.primary).id();
  public final Column name = Column.make("name", Column.name, Column.table, Column.dataSpec, Column.indexed, Column.primary).varchar(20);

  public PlayerTable() {
    super("player");
    engine = StorageEngine.transactional;
  }

}
