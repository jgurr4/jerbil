package com.ple.jerbil.testcommon;

import com.ple.jerbil.data.Database;
import com.ple.jerbil.data.Immutable;
import com.ple.jerbil.data.StorageEngine;
import com.ple.jerbil.data.query.Table;
import com.ple.jerbil.data.selectExpression.Column;
import com.ple.jerbil.data.selectExpression.NumericExpression.NumericColumn;
import com.ple.jerbil.data.selectExpression.StringColumn;
import com.ple.util.IArrayList;
import com.ple.util.IList;

@Immutable
public class PlayerTable extends Table {
  public final NumericColumn playerId;
  public final NumericColumn userId;
  public final StringColumn name;
  public final PlayerTableColumns columns;

  public PlayerTable(Database db) {
    super("player", db, StorageEngine.transactional);
    playerId = Column.make("playerId", this).id();
    userId = Column.make("userId", this).asInt();
    name = Column.make("name", this).asVarchar(20);
    columns = new PlayerTableColumns(playerId, userId, name);
  }

}
